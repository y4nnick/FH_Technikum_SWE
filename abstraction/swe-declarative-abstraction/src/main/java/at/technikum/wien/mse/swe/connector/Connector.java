package at.technikum.wien.mse.swe.connector;

import at.technikum.wien.mse.swe.exception.ConnectorReadException;
import at.technikum.wien.mse.swe.exception.SecurityAccountOverviewReadException;
import at.technikum.wien.mse.swe.model.RiskCategory;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class Connector {

    public static <T> T read(Path path, Class<T> classType) {

        String content = Connector.readFileContent(path);

        try {
            T instance = classType.newInstance();

            for (Field field : classType.getDeclaredFields()) {
                StructureField[] structureFields = field.getAnnotationsByType(StructureField.class);

                Class objectClass = field.getType();

                Boolean fieldAccessible = field.isAccessible();
                field.setAccessible(true);

                for (StructureField structureField : structureFields) {
                    String value = Connector.getRawValueOfField(structureField, content);

                    if (String.class.equals(objectClass)) {
                        Connector.setValueOnField(field, instance, value);
                    } else if (RiskCategory.class.equals(objectClass)) {
                        Connector.setValueOnField(field, instance,  RiskCategory.fromCode(value).get());
                    }
                    else if (!field.getType().isPrimitive()) {

                        // Create or get the object instance
                        Object objectInstance = Connector.getObjectInstanceFromField(field, instance);

                        // Get the field with the correct name from the object instance
                        Field objectField = objectClass.getDeclaredField(structureField.name());

                        // Set the value on the instance field
                        if (String.class.equals(objectField.getType())) {
                            Connector.setValueOnField(objectField, objectInstance, value);
                        } else if (BigDecimal.class.equals(objectField.getType())) {
                            Connector.setValueOnField(objectField, objectInstance, new BigDecimal(value));
                        }

                        // Set the object instance on the root object field
                        Connector.setValueOnField(field, instance, objectInstance);
                    }
                }

                field.setAccessible(fieldAccessible);
            }

            return instance;
        } catch (Exception e) {
            throw new SecurityAccountOverviewReadException(e);
        }
    }

    /**
     * Delivers an object instance for the given field on the given instance.
     * If the object already exists on the instance we will return it.
     * Otherwise we are creating a new instance with `null` parameters.
     */
    private static Object getObjectInstanceFromField(Field field, Object instance) throws Exception {
        Class objectClass = field.getType();
        Object objectInstance = field.get(instance);

        // If the instance already exists we just return it
        if (objectInstance != null) {
            return objectInstance;
        }

        Constructor[] constructors = objectClass.getConstructors();

        // If we have no constructor we can just use the default constructor of the Class.
        if (constructors == null || constructors.length == 0) {
            return objectClass.newInstance();
        }

        // Otherwise we call the first constructor with the correct amount of null objects)
        return constructors[0].newInstance(new Object[constructors[0].getParameterCount()]);
    }

    /**
     * Sets the given field in the instance to the given value
     * We are ignoring restrictions like `private` or `final`
     * @throws Exception
     */
    private static void setValueOnField(Field field, Object instance, Object value) throws Exception {
        // Remove the final from the field
        int modifierValue = field.getModifiers();
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        Boolean modifiersFieldAccessible = modifiersField.isAccessible();
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        // Make the field accessible
        Boolean accessible = field.isAccessible();
        field.setAccessible(true);

        // Set the value
        field.set(instance, value);

        // Set back accessibility and modifier
        field.setAccessible(accessible);
        modifiersField.setInt(field, modifierValue);
        modifiersField.setAccessible(modifiersFieldAccessible);
    }

    /**
     * Delivers the raw value from the input.
     * According to the begin, length, padding and alignment values of the passed StructureField.
     */
    private static String getRawValueOfField(StructureField field, String input) {

        // Get value based on begin and length
        String value = input.substring(field.begin() - 1, field.begin() + field.length() - 1);

        // Remove Padding
        if (field.alignment() == FieldAlignment.RIGHT) {
            while (!value.isEmpty() && value.startsWith(field.padding() + "")) {
                value = value.substring(1);
            }
        } else {
            while (!value.isEmpty() && value.endsWith(field.padding() + "")){
                value = value.substring(0, value.length() - 1);
            }
        }

        return value;
    }

    /**
     * Reads out the content of the given file
     * @throws ConnectorReadException if the file couldn't be read
     */
    private static String readFileContent(Path file) {
        String content;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            content = reader.readLine();
        } catch (IOException e) {
            throw new ConnectorReadException(e);
        }
        return content;
    }
}
