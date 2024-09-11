Tener instalado lombok en el IDE: 
https://projectlombok.org/download

Los logs quedan en la siguiente ruta: C:\logs\pruebaLinkTic.log

Para el Swagger: 
http://localhost:8080/swagger-ui/index.html#/
http://localhost:8080/api-docs

Se agrega seguridad al API, de tal mod que:

1.http://localhost:8080/api/user/create    (POST)
2.http://localhost:8080/api/user/getAll    (GET)
3.http://localhost:8080/api/auth/login?email=juan.perez@example.com&password=admin     (POST)

son las únicas rutas sin proteger debido a que se usa una base de datos h2.

la secuencia sería, crear el usuario (1):

{
  "name": "Juan Perez",
  "email": "juan.perez@example.com",
  "phone": "3234567890",
  "password": "admin",
  "dateCreation": "2024-09-10T10:30:00"
}

listarlo en caso de que necesite consultar alguna información (2).
Realizar el login (3) para obtener el token (JWT).

con el token crear alguna categoria ya que al crear el producto ya debe existir almenos una categoria:
http://localhost:8080/api/categoria/create  (POST)

{
  "name": "TECNOLOGIA",
  "status": "ACTIVO"
}
