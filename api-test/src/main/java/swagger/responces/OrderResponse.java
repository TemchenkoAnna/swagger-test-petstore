package swagger.responces;

import lombok.Data;

@Data
public class OrderResponse{
	private int petId;
	private int quantity;
	private int id;
	private String shipDate;
	private boolean complete;
	private String status;
}