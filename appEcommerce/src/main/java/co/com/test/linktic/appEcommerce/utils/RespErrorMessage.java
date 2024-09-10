package co.com.test.linktic.appEcommerce.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespErrorMessage {
	   private  int code;
	   private  String message;
}
