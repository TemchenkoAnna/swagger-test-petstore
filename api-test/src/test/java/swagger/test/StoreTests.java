package swagger.test;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import swagger.ProjectConfig;
import swagger.services.StoreApiService;

import java.io.IOException;
import java.util.Locale;

import static swagger.conditions.Conditions.jsonSchema;

@Epic("Petstore API")
@Feature("Store Operations")
public class StoreTests {
    private final StoreApiService storeApiService = new StoreApiService();
    private static Faker faker;

    @BeforeAll
    public static void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
        RestAssured.baseURI = config.baseUrl();
        faker = new Faker(new Locale(config.locale()));
    }


    @Story("Get store inventory")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetStoreInventory() throws IOException {
        storeApiService.getInventory()
                .shouldHave(jsonSchema("/inventory-schema.json"));
    }
}
