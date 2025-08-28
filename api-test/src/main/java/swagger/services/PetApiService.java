package swagger.services;


import io.qameta.allure.Step;
import swagger.assertions.AssertableResponse;
import swagger.payloads.PetPayload;

public class PetApiService extends ApiService {
    @Step
    public AssertableResponse addPetToStore(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .post("pet"));
    }

    @Step
    public AssertableResponse updatePet(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .put("pet"));
    }

    @Step
    public AssertableResponse getPetByTag(String tagName) {
        return new AssertableResponse(setup()
                .basePath("pet/findByTags")
                .and()
                .queryParam("tags", tagName)
                .get());
    }

    @Step
    public AssertableResponse getPetById(int id) {
        return new AssertableResponse(setup()
                .basePath("/pet/{id}")
                .and()
                .pathParam("id", id)
                .get());
    }

    @Step
    public AssertableResponse getPetByStatus(String status) {
        return new AssertableResponse(setup()
                .basePath("pet/findByStatus")
                .and()
                .queryParam("status", status)
                .get());
    }
}
