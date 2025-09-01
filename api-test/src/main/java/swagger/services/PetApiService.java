package swagger.services;


import io.qameta.allure.Step;
import swagger.assertions.AssertableResponse;
import swagger.payloads.PetPayload;

public class PetApiService extends ApiService {
    public static final String BASE_PATH = "pet";

    @Step("Add pet to the store")
    public AssertableResponse addPetToStore(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .post(BASE_PATH));
    }

    @Step("Update pet")
    public AssertableResponse updatePet(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .put(BASE_PATH));
    }

    @Step("Get pet by tag")
    public AssertableResponse getPetByTag(String tagName) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/findByTags")
                .and()
                .queryParam("tags", tagName)
                .get());
    }

    @Step("Get pet by ID")
    public AssertableResponse getPetById(int id) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/{id}")
                .and()
                .pathParam("id", id)
                .get());
    }

    @Step("Get pet by status")
    public AssertableResponse getPetByStatus(String status) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/findByStatus")
                .and()
                .queryParam("status", status)
                .get());
    }
}
