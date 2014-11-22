package cucumber.runtime.java.blueprint;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface BlueprintDescriptorLocation {
    String value() default "";
}
