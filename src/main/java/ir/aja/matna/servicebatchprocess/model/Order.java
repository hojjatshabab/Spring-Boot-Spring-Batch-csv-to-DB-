package ir.aja.matna.servicebatchprocess.model;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class Order {

	private Long orderId;

	private String firstName;

	private String lastName;

	private String email;

	private BigDecimal cost;

	private String itemId;

	private String itemName;

	private Date shipDate;


}
