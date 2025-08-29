package swagger.services;

import io.qameta.allure.Step;
import swagger.assertions.AssertableResponse;
import swagger.payloads.OrderPayload;

public class StoreApiService extends ApiService {
    public static final String BASE_PATH = "store";

    @Step
    public AssertableResponse getInventory() {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/inventory")
                .when()
                .get());
    }

    @Step
    public AssertableResponse placeOrder(OrderPayload order) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/order")
                .body(order)
                .when()
                .post());
    }

    @Step
    public AssertableResponse getOrder(int id) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/order" + "/{id}")
                .and()
                .pathParam("id", id)
                .when()
                .get());
    }

    public AssertableResponse deleteOrder(int id) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH +"/order"+ "/{id}")
                .and()
                .pathParam("id", id)
                .when()
                .delete());
    }
}
