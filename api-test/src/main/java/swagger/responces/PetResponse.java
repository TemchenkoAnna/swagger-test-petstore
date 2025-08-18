package swagger.responces;

import lombok.Getter;

import java.util.List;

@Getter
public class PetResponse {
    private List<String> photoUrls;
    private String name;
    private int id;
    private Category category;
    private List<TagsItem> tags;
    private String status;
}