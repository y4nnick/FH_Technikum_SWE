package at.technikum.wien.mse.swe.connector;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface StructureFields {
    StructureField[] value();
}