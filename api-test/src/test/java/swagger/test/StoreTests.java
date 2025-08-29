package swagger.test;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import swagger.ProjectConfig;
import swagger.payloads.OrderPayload;
import swagger.responces.OrderResponse;
import swagger.services.StoreApiService;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static swagger.conditions.Conditions.*;

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

    @Story("Place order for Pet")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testPlaceOrderForPet() {
        String shipDate = Instant.now().truncatedTo(ChronoUnit.MILLIS).toString();
        OrderPayload order = new OrderPayload()
                .id(10)
                .petId(10)
                .quantity(1)
                .shipDate(shipDate)
                .status("approved")
                .complete(true);
        OrderResponse response = storeApiService.placeOrder(order)
                .shouldHave(statusCode(200))
                .asPojo(OrderResponse.class);
        assertEquals(shipDate, Instant.parse(response.getShipDate()).truncatedTo(ChronoUnit.MILLIS).toString());
        assertEquals(10, response.getId());
        assertEquals(1, response.getQuantity());
        assertTrue(response.isComplete());
        assertEquals("approved", response.getStatus());
    }

    @Story("Get order for Pet")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testGetOrderForPet() {

        int id = faker.random().nextInt(1, 5);
        OrderPayload order = new OrderPayload()
                .id(id)
                .petId(10)
                .quantity(1)
                .shipDate(Instant.now().toString())
                .status("approved")
                .complete(true);
        storeApiService.placeOrder(order);
        storeApiService.getOrder(id)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", Matchers.is(id)));
    }

    @Story("Delete order for Pet")
    @Severity(SeverityLevel.NORMAL)
    @Test
      public void testDeleteOrderForPet() {
        int id = faker.random().nextInt(1, 5);
        OrderPayload order = new OrderPayload()
                .id(id)
                .petId(10)
                .quantity(1)
                .shipDate(Instant.now().toString())
                .status("approved")
                .complete(true);
        storeApiService.placeOrder(order);
        storeApiService.deleteOrder(id)
                .shouldHave(statusCode(200));

    }
}
