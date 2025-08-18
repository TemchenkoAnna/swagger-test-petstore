package swagger.assertions;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import swagger.conditions.Condition;

@RequiredArgsConstructor
@Slf4j
public class AssertableResponse {
    private Response response;

    public AssertableResponse(Response pet) {
        this.response = pet;
    }

    public AssertableResponse shouldHave(Condition condition) {
        log.info("About to check condition {}", condition);
        condition.check(response);
        return this;
    }

    public <T> T asPojo(Class<T> tClass) {
        return response.as(tClass);
    }

    public Headers headers() {
        return response.getHeaders();
    }
}
