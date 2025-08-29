package swagger.services;


import io.qameta.allure.Step;
import swagger.assertions.AssertableResponse;
import swagger.payloads.PetPayload;

public class PetApiService extends ApiService {
    public static final String BASE_PATH = "pet";

    @Step
    public AssertableResponse addPetToStore(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .post(BASE_PATH));
    }

    @Step
    public AssertableResponse updatePet(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .put(BASE_PATH));
    }

    @Step
    public AssertableResponse getPetByTag(String tagName) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/findByTags")
                .and()
                .queryParam("tags", tagName)
                .get());
    }

    @Step
    public AssertableResponse getPetById(int id) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/{id}")
                .and()
                .pathParam("id", id)
                .get());
    }

    @Step
    public AssertableResponse getPetByStatus(String status) {
        return new AssertableResponse(setup()
                .basePath(BASE_PATH + "/findByStatus")
                .and()
                .queryParam("status", status)
                .get());
    }
}
