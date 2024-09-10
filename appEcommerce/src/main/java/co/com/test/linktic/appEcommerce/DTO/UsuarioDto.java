package co.com.test.linktic.appEcommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

	private Integer id;
	private String nombre;
	private String password;
	private String email;
	private String nombrePersona;
}
