package swagger.conditions;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;

import java.io.IOException;

@UtilityClass
public class Conditions {
    public static StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public static BodyFieldCondition bodyField(String jsonPath, Matcher matcher){
        return new BodyFieldCondition(jsonPath, matcher);

    }

    public JsonSchemaValidation jsonSchema(String jsonPath) throws IOException {
        return new JsonSchemaValidation(jsonPath);
    }
}
