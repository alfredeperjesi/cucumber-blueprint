package cucumber.runtime.java.blueprint;

import cucumber.runtime.CucumberException;
import cucumber.runtime.java.ObjectFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BlueprintObjectFactory extends CamelBlueprintTestSupport implements ObjectFactory {
    private static final int TIMEOUT = 30000;
    public static final String COMMA = ",";

    private final Map<Class<?>, Object> stepDefinitionsInstances = new HashMap<>();

    private String blueprintDescriptiors;

    @Override
    protected String getBlueprintDescriptor() {
        return blueprintDescriptiors;
    }

    @Override
    public void start() {
        if (blueprintDescriptiors != null) {
            setUpBlueprintContext();
            for (Object stepDefinitionsInstance : stepDefinitionsInstances.values()) {
                injectDependencies(stepDefinitionsInstance.getClass(), stepDefinitionsInstance);
            }
        }
    }

    private void setUpBlueprintContext() {
        try {
            setUp();
        } catch (Exception e) {
            throw new CucumberBlueprintContextException(String.format("Blueprint context initialisation failed by descriptiors %s.", blueprintDescriptiors), e);
        }
    }

    private void injectDependencies(final Class<?> stepDefinitionsClass, final Object stepDefinitionsInstance) {
        injectDeclaredDependencies(stepDefinitionsClass, stepDefinitionsInstance);
        if (!stepDefinitionsClass.getSuperclass().equals(Object.class)) {
            injectDependencies(stepDefinitionsClass.getSuperclass(), stepDefinitionsInstance);
        }
    }

    private void injectDeclaredDependencies(final Class<?> stepDefinitionsClass, final Object stepDefinitionsInstance) {
        for (Field field : stepDefinitionsClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                DependencyInjectionStrategy dependencyInjectionStrategy = DependencyInjectionStrategy.find(field.getType());
                dependencyInjectionStrategy.injectDependency(field, stepDefinitionsInstance, this);
            }
        }
    }

    @Override
    public void stop() {
        try {
            stepDefinitionsInstances.clear();
            tearDown();
        } catch (Exception e) {
            throw new CucumberBlueprintContextException("Error during tear down", e);
        }
    }

    @Override
    public void addClass(final Class<?> stepDefinitionsClass) {
        Object stepDefinitionsInstance = stepDefinitionsClass.cast(stepDefinitionsInstances.get(stepDefinitionsClass));
        if (stepDefinitionsInstance == null) {
            verifyBlueprintContext(stepDefinitionsClass);
            createNewInstance(stepDefinitionsClass);
        }
    }

    private void verifyBlueprintContext(final Class<?> stepDefinitionsClass) {
        if (stepDefinitionsClass.isAnnotationPresent(BlueprintDescriptorLocation.class)) {
            String blueprintDescriptiors = stepDefinitionsClass.getAnnotation(BlueprintDescriptorLocation.class).value();
            if (areBlueprintDescriptiorsDifferent(blueprintDescriptiors)) {
                throw new CucumberBlueprintContextException(String.format("Mismatched blueprint descriptors on step definitions: %s and %s", stepDefinitionsClass.getName(), stepDefinitionsInstances.values().iterator().next().getClass().getName()));
            }
            if (this.blueprintDescriptiors == null) {
                this.blueprintDescriptiors = blueprintDescriptiors;
            }
        }
    }

    private boolean areBlueprintDescriptiorsDifferent(final String blueprintDescriptiors) {
        return this.blueprintDescriptiors != null && !areBlueprintDescriptorsTheSame(blueprintDescriptiors);
    }

    private boolean areBlueprintDescriptorsTheSame(final String blueprintDescriptiors) {
        String[] newBlueprintDescriptiors = blueprintDescriptiors.split(COMMA);
        String[] oldBlueprintDescriptiors = this.blueprintDescriptiors.split(COMMA);
        return isContained(newBlueprintDescriptiors, oldBlueprintDescriptiors) &&
                isContained(oldBlueprintDescriptiors, newBlueprintDescriptiors);

    }

    private boolean isContained(final String[] containables, final String[] bases) {
        for (String containable : containables) {
            boolean found = false;
            for (String base : bases) {
                if (containable.trim().equals(base.trim())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private <T> void createNewInstance(final Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor();
            T instance = constructor.newInstance();
            stepDefinitionsInstances.put(type, instance);
        } catch (NoSuchMethodException e) {
            throw new CucumberException(String.format("%s doesn't have an empty constructor", type.getName()), e);
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type.getName()), e);
        }
    }

    @Override
    public <T> T getInstance(final Class<T> stepDefinitionsClass) {
        T stepDefinitionsInstance = stepDefinitionsClass.cast(stepDefinitionsInstances.get(stepDefinitionsClass));
        if (stepDefinitionsInstance == null) {
            throw new CucumberException(String.format("Step definitions instance is not found for class %s", stepDefinitionsClass.getName()));
        }
        return stepDefinitionsInstance;
    }

    @SuppressWarnings("unchecked")
    private static enum DependencyInjectionStrategy {
        PRODUCER_TEMPLATE {
            @Override
            protected boolean isAssignableBy(final Class<?> dependencyClass) {
                return dependencyClass.isAssignableFrom(ProducerTemplate.class);
            }

            @Override
            protected <T> T getDependency(final BlueprintObjectFactory blueprintObjectFactory, final Class<T> dependencyClass) {
                return (T) blueprintObjectFactory.template();
            }
        },
        CAMEL_CONTEXT {
            @Override
            protected boolean isAssignableBy(final Class<?> dependencyClass) {
                return dependencyClass.isAssignableFrom(CamelContext.class);
            }

            @Override
            protected <T> T getDependency(final BlueprintObjectFactory blueprintObjectFactory, final Class<T> dependencyClass) {
                return (T) blueprintObjectFactory.context();
            }
        },
        OSGI_SERVICE {
            @Override
            protected boolean isAssignableBy(final Class<?> dependencyClass) {
                return !PRODUCER_TEMPLATE.isAssignableBy(dependencyClass) &&
                        !CAMEL_CONTEXT.isAssignableBy(dependencyClass);
            }

            @Override
            protected <T> T getDependency(final BlueprintObjectFactory blueprintObjectFactory, final Class<T> dependencyClass) {
                return blueprintObjectFactory.getOsgiService(dependencyClass, TIMEOUT);
            }
        };

        private static DependencyInjectionStrategy find(final Class<?> dependencyClass) {
            for (DependencyInjectionStrategy dependencyInjectionStrategy : DependencyInjectionStrategy.values()) {
                if (dependencyInjectionStrategy.isAssignableBy(dependencyClass)) {
                    return dependencyInjectionStrategy;
                }
            }
            throw new CucumberBlueprintDependencyInjectionException(String.format("Unable to find dependency injection strategy for dependency class %s", dependencyClass.getName()));
        }

        protected abstract boolean isAssignableBy(final Class<?> dependencyClass);

        private <T> void injectDependency(final Field dependencyField, final T stepDefinitionsInstance, final BlueprintObjectFactory blueprintObjectFactory) {
            try {
                dependencyField.setAccessible(true);
                dependencyField.set(stepDefinitionsInstance, getDependency(blueprintObjectFactory, dependencyField.getType()));
            } catch (Exception e) {
                throw new CucumberBlueprintDependencyInjectionException(String.format("Unable to set dependency %s.%s", dependencyField.getDeclaringClass().getName(), dependencyField.getName()), e);
            } finally {
                dependencyField.setAccessible(false);
            }
        }

        protected abstract <T> T getDependency(final BlueprintObjectFactory blueprintObjectFactory, final Class<T> dependencyClass);
    }
}
