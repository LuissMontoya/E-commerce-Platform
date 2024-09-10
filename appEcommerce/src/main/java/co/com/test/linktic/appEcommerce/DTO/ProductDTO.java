package co.com.test.linktic.appEcommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer stock;
	private Integer category_id;
}
