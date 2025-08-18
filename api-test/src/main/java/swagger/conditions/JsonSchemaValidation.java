package swagger.conditions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class JsonSchemaValidation implements Condition {
    private final String json;

    public JsonSchemaValidation(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream fileStream = this.getClass().getResourceAsStream(filePath);
        Object fileObject = mapper.readValue(fileStream, Object.class);
        json = mapper.writeValueAsString(fileObject);
    }

    @Override
    public void check(Response response) {
        response.then().assertThat().body(matchesJsonSchema(json));
    }
}
