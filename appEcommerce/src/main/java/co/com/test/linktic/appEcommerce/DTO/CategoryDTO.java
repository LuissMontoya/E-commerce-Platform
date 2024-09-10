package co.com.test.linktic.appEcommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	
	private Integer id;
	private String name;
	private String status;
}
