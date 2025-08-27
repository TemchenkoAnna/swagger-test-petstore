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
    public AssertableResponse updatePet(PetPayload pet, int id) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .put("pet"));
    }

    @Step
    public AssertableResponse getPetByTag(String tagName) {
        return new AssertableResponse(setup()
                .when()
                .get("/pet/findByTags?tags=" + tagName));
    }
}
