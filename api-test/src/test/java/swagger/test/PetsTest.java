package swagger.test;

import com.github.javafaker.Faker;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import swagger.ProjectConfig;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import swagger.payloads.PetPayload;
import swagger.responces.PetResponse;
import swagger.services.PetApiService;
import org.aeonbits.owner.ConfigFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static swagger.conditions.Conditions.*;

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

    @Story("Update pet's status")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testUpdatePetById() {
        String name = faker.funnyName().name();
        int id = faker.random().nextInt(1, 1000);

        PetPayload pet = petCreateService
                .createStandardPetWithParams(name, "Cat", id, "available", "Breed");
        petApiService.addPetToStore(pet);

        PetPayload updatedPet = petCreateService
                .createStandardPetWithParams(name, "Cat", id, "sold", "Breed");

        PetResponse response = petApiService.updatePet(updatedPet)
                .shouldHave(statusCode(200))
                .asPojo(PetResponse.class);
        assertEquals("sold", response.getStatus());
        assertEquals(id, response.getId());
    }

    @Story("Get pets by tag")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetPetByTag() {
        String name = faker.funnyName().name();
        String tagName = "TestTagString354";
        int id = faker.random().nextInt(1, 1000);

        PetPayload pet = petCreateService
                .createStandardPetWithParams(name, "Cat", id, "available", tagName);
        petApiService.addPetToStore(pet);
        petApiService.getPetByTag(tagName)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("[0].tags[0].name", Matchers.is(tagName)));

    }

    @Story("Get pets by id")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testGetPetById() {
        String name = faker.funnyName().name();
        int id = faker.random().nextInt(1, 1000);
        PetPayload pet = petCreateService
                .createStandardPetWithParams(name, "Dog", id, "available", "Breed");

        petApiService.addPetToStore(pet);
        petApiService.getPetById(id)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("name", Matchers.is(name)));
    }

    @Story("Get pets by status")
    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest
    @CsvSource({"available",
            "pending",
            "sold"})
    public void testGetPetByStatus(String status) {

        petApiService.getPetByStatus(status)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("status", Matchers.everyItem(equalTo(status))))
                .shouldHave(bodyField("$", Matchers.notNullValue()));

    }

    @Story("Negative: Get pets by Non existing tag")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testNonExistingGetPetByTag() {

        String tagName = faker.funnyName().name();

        petApiService.getPetByTag(tagName)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("", Matchers.hasSize(0)));

    }

    @Story("Negative: Get pets by empty and incorrect status")
    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest
    @CsvSource({"''",
            "created",
            "123"})
    public void testGetPetByIncorrectStatus(String status) {

        petApiService.getPetByStatus(status)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", Matchers.containsString("is not in the allowable values")));

    }
}
