package swagger.services;


import io.qameta.allure.Step;
import swagger.assertions.AssertableResponse;
import swagger.payloads.PetPayload;

public class PetApiService extends ApiService {
    public static final String basePath = "pet";

    @Step
    public AssertableResponse addPetToStore(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .post(basePath));
    }

    @Step
    public AssertableResponse updatePet(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .put(basePath));
    }

    @Step
    public AssertableResponse getPetByTag(String tagName) {
        return new AssertableResponse(setup()
                .basePath(basePath + "/findByTags")
                .and()
                .queryParam("tags", tagName)
                .get());
    }

    @Step
    public AssertableResponse getPetById(int id) {
        return new AssertableResponse(setup()
                .basePath(basePath + "/{id}")
                .and()
                .pathParam("id", id)
                .get());
    }

    @Step
    public AssertableResponse getPetByStatus(String status) {
        return new AssertableResponse(setup()
                .basePath(basePath + "/findByStatus")
                .and()
                .queryParam("status", status)
                .get());
    }
}
