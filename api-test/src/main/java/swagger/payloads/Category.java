package swagger.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class Category{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

}