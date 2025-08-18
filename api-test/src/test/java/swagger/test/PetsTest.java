package swagger.test;

import com.github.javafaker.Faker;
import swagger.ProjectConfig;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import swagger.payloads.Category;
import swagger.payloads.PetPayload;
import swagger.payloads.TagsItem;
import swagger.responces.PetResponse;
import swagger.services.PetApiService;
import org.aeonbits.owner.ConfigFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static swagger.conditions.Conditions.statusCode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Locale;


@Epic("Petstore API")
@Feature("Pet Operations")

public class PetsTest {
    private final PetApiService petApiService = new PetApiService();
    private static Faker faker;
    private final String CATEGORY = "Dog";

    @BeforeAll
    public static void setUp() {
      ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
        RestAssured.baseURI = config.baseUrl();
         faker = new Faker(new Locale(config.locale()));
    }

    @Story("Create a pet with valid data")
    @Severity(SeverityLevel.CRITICAL)

    @Test
    public void testCanAddPetToTheStore() {
        String name = faker.funnyName().name();
        int id = faker.random().nextInt(1, 100);
        Category category = new Category()
                .id(1)
                .name(CATEGORY);
        TagsItem item = new TagsItem()
                .id(1)
                .name("breed");
        ArrayList<TagsItem> tags = new ArrayList<>();
        tags.add(item);
        ArrayList<String> photos = new ArrayList<>();
        photos.add(faker.shakespeare().hamletQuote());
        PetPayload pet = new PetPayload()
                .id(id)
                .name(name)
                .category(category)
                .tags(tags)
                .status("available")
                .photoUrls(photos);

        PetResponse response = petApiService.addPetToStore(pet)
                .shouldHave(statusCode(200))
                .asPojo(PetResponse.class);
        assertEquals(name, response.getName());
        assertEquals(id, response.getId());
    }



}
