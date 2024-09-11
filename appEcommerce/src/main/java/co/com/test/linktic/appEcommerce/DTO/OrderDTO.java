package co.com.test.linktic.appEcommerce.DTO;


import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	private Integer id;
	private Date date;
	private String customerName;
	private Double totalAmount;
	private List<Integer> products;
}
