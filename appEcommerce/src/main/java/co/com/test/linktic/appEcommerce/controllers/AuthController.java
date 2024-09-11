package co.com.test.linktic.appEcommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import co.com.test.linktic.appEcommerce.entity.Users;
import co.com.test.linktic.appEcommerce.service.impl.UsersServiceImpl;
import co.com.test.linktic.appEcommerce.utils.Constants;
import co.com.test.linktic.appEcommerce.utils.JwtTokenUtil;
import co.com.test.linktic.appEcommerce.utils.Utils;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST,RequestMethod.PUT })
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private UsersServiceImpl usersServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestParam String email, @RequestParam String password) {
        Users user = usersServiceImpl.findByEmail(email);
        ResponseDTO response = null;
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
        	response =  Utils.mapearRespuesta(Constants.CONSULTA_EXITOSAMENTE, 
        			Constants.CODIGO_200,jwtTokenUtil.generateToken(user.getEmail()));
        } else {
        	response =  Utils.mapearRespuesta(Constants.CONSULTA_ERROR, 
        			Constants.CODIGO_500);
        }
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.ACCEPTED);
    }
}
