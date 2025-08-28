package swagger.services;

import io.qameta.allure.Step;
import swagger.assertions.AssertableResponse;

public class StoreApiService extends ApiService {
    public static final String basePath = "store";

    @Step
    public AssertableResponse getInventory() {
        return new AssertableResponse(setup()
                .basePath(basePath + "/inventory")
                .get());
    }
}
