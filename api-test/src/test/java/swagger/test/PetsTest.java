package swagger.test;

import com.github.javafaker.Faker;
import org.hamcrest.Matchers;
import swagger.ProjectConfig;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import swagger.conditions.Conditions;
import swagger.payloads.PetPayload;
import swagger.responces.PetResponse;
import swagger.services.PetApiService;
import org.aeonbits.owner.ConfigFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static swagger.conditions.Conditions.statusCode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import swagger.services.PetCreateService;

import java.util.Locale;


@Epic("Petstore API")
@Feature("Pet Operations")

public class PetsTest {
    private final PetApiService petApiService = new PetApiService();
    private final PetCreateService petCreateService = new PetCreateService();
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
        int id = faker.random().nextInt(1, 1000);

        PetPayload pet = petCreateService
                .createStandardPetWithParams(name, "Dog", id, "available", "Breed");

        PetResponse response = petApiService.addPetToStore(pet)
                .shouldHave(statusCode(200))
                .asPojo(PetResponse.class);
        assertEquals(name, response.getName());
        assertEquals(id, response.getId());
    }

    @Test
    public void updatePetById() {
        String name = faker.funnyName().name();
        int id = faker.random().nextInt(1, 1000);

        PetPayload pet = petCreateService
                .createStandardPetWithParams(name, "Cat", id, "available", "Breed");
        petApiService.addPetToStore(pet);

        PetPayload updatedPet = petCreateService
                .createStandardPetWithParams(name, "Cat", id, "sold", "Breed");

        PetResponse response = petApiService.updatePet(updatedPet, id)
                .shouldHave(statusCode(200))
                .asPojo(PetResponse.class);
        assertEquals("sold", response.getStatus());
        assertEquals(id, response.getId());
    }

    @Test
    public void getPetByTag() {
        String name = faker.funnyName().name();
        String tagName = "TestTagString354";
        int id = faker.random().nextInt(1, 1000);

        PetPayload pet = petCreateService
                .createStandardPetWithParams(name, "Cat", id, "available", tagName);
        petApiService.addPetToStore(pet);
        petApiService.getPetByTag(tagName)
                .shouldHave(statusCode(200))
                .shouldHave(Conditions.bodyField("[0].tags[0].name", Matchers.is(tagName)));

    }
}

