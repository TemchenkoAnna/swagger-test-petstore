package swagger.services;


import swagger.assertions.AssertableResponse;
import swagger.payloads.PetPayload;

public class PetApiService extends ApiService {

    public AssertableResponse addPetToStore(PetPayload pet) {
        return new AssertableResponse(setup()
                .body(pet)
                .when()
                .post("pet"));
    }
}
