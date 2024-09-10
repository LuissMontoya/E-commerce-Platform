package co.com.test.linktic.appEcommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.test.linktic.appEcommerce.DTO.ResponseDTO;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/usuario")
@Tag(name = "Usuario - Controller", description = "Controller encargado de gestionar las operaciones del usuario.")
@CrossOrigin(origins = "*", methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.PUT })
@RequiredArgsConstructor
public class UsuarioController {
	/**
	 * Atributo que inyecta una instancia de la interfaz que contiene los métodos
	 * que seran usados en este controlador.
	 */
	private final UsuarioServiceImpl serviceUsuario;

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;

	/**
	 * Método de tipo POST que permite validar un objeto de tipo (Usuario).
	 */
	@Operation(summary = "Operación que permite validar el usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Se ha creado satisfactoriamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "La petición no puede ser entendida por el servidor debido a errores de sintaxis, el cliente no debe repetirla no sin antes hacer modificaciones", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no puede ser encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Se presento una condición inesperada que impidió completar la petición", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
	@PostMapping("/validar")
	public ResponseEntity<ResponseDTO> validarUsuario(@RequestBody UsuarioDto usuario) {
		ResponseEntity<ResponseDTO> response = this.serviceUsuario.auntenticar(usuario);

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(usuario.getNombre(), usuario.getPassword()));
			final UserDetails userDetails = this.serviceUsuario.loadUserByUsername(usuario.getNombre());
			final String token = jwtTokenUtil.generateToken(userDetails);
			UsuarioDto usuarioTmp = (UsuarioDto) response.getBody().getObjectResponse();
			usuarioTmp.setToken(Constantes.BEARER + token);
			response.getBody().setObjectResponse(usuarioTmp);
		}
		return response;
	}

	@Operation(summary = "Operación que permite consultar los usuarios")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Se consulta exitosamente", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = com.bbraun.dtos.ResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "La petición no puede ser entendida por el servidor debido a errores de sintaxis, el cliente no debe repetirla no sin antes hacer modificaciones", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no puede ser encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Se presento una condición inesperada que impidió completar la petición", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
	@GetMapping
	public ResponseEntity<ResponseDTO> obtenerHistorial() {
		return this.serviceUsuario.obtenerUsuarios();
	}

	@Operation(summary = "Operación que permite guardar el usuario ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Se consulta exitosamente", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = com.bbraun.dtos.ResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "La petición no puede ser entendida por el servidor debido a errores de sintaxis, el cliente no debe repetirla no sin antes hacer modificaciones", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no puede ser encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Se presento una condición inesperada que impidió completar la petición", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
	@PostMapping("/guardar")
	public ResponseEntity<ResponseDTO> guardarUsuario(@RequestBody UsuarioDto usuario) {
		return this.serviceUsuario.guardarUsuario(usuario);
	}
	
	@Operation(summary = "Operación que permite consultar un usuario")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Se consulta exitosamente", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = com.bbraun.dtos.ResponseDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "La petición no puede ser entendida por el servidor debido a errores de sintaxis, el cliente no debe repetirla no sin antes hacer modificaciones", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "El recurso solicitado no puede ser encontrado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }),
			@ApiResponse(responseCode = "500", description = "Se presento una condición inesperada que impidió completar la petición", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class)) }), })
	@GetMapping("/find-user-name")
	public ResponseEntity<ResponseDTO> getUser(@RequestParam(required = true) String usuario) {
		return this.serviceUsuario.findUserByName(usuario);
	}
}
