package swagger.services;

import io.qameta.allure.Step;
import swagger.payloads.Category;
import swagger.payloads.PetPayload;
import swagger.payloads.TagsItem;

import java.util.ArrayList;

public class PetCreateService {

    @Step("Create standard pet")
    public PetPayload createStandardPetWithParams(String name, String category, int id, String status, String tagName) {
        Category categoryObject = new Category()
                .id(1)
                .name(category);
        TagsItem item = new TagsItem()
                .id(1)
                .name(tagName);
        ArrayList<TagsItem> tags = new ArrayList<>();
        tags.add(item);
        ArrayList<String> photos = new ArrayList<>();
        photos.add("test string for photo");
        return new PetPayload()
                .id(id)
                .name(name)
                .category(categoryObject)
                .tags(tags)
                .status(status)
                .photoUrls(photos);
    }
}
