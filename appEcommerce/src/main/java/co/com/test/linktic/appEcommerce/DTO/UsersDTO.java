package co.com.test.linktic.appEcommerce.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
	private Integer id;
	private String name;
	private String email;
	private String password;
	private String phone;
	private Date dateCreation;
}
