# Poc-feature-flag

Mecanismo para tener la misma url pero con diferentes comportamientos en funcion de un parametro, en este caso de la cabecera "FeatureFlag". 
Si se realiza una llamada con un feature flag que no existe se devuelve un 404.

# Feature flag:
- one
- two
- three


# Prerrequisitos
- Java 8
- Maven

# Ejecucion

```
mvn spring-boot:run
```

# Ejemplos

```
curl -X GET \
  http://localhost:8080/poc \
  -H 'Cache-Control: no-cache' \
  -H 'FeatureFlag: one' 
```
Respuesta: onethree

```
curl -X GET \
  http://localhost:8080/poc \
  -H 'Cache-Control: no-cache' \
  -H 'FeatureFlag: two' 
```
Respuesta: two

```
curl -X GET \
  http://localhost:8080/poc \
  -H 'Cache-Control: no-cache' \
  -H 'FeatureFlag: three' 
```
Respuesta: onethree

```
curl -X GET \
  http://localhost:8080/poc 
```
Respuesta: NO FEATURES

```
curl -X GET \
  http://localhost:8080/poc \
  -H 'Cache-Control: no-cache' \
  -H 'FeatureFlag: four' 
```
Respuesta: Error redirigido a /error