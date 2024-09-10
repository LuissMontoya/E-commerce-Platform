package co.com.test.linktic.appEcommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
	private Integer statusCode;
	private String message;
	private Object objectResponse;
}
