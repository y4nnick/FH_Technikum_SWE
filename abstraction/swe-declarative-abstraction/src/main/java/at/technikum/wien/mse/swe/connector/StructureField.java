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
    FieldAlignment alignment();

    /**
     * Field name in the nested object.
     */
    String name() default "";
}