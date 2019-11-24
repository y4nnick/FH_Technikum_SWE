package at.technikum.wien.mse.swe.connector;


import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(StructureFields.class)
public @interface StructureField {
    int begin();
    int length();
    char padding() default ' ';
    String name() default "";
    FieldAlignment alignment();
}