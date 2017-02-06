# POC Fuse Hipotecario

Proyecto de integración de Fuse.

Involucra los siguientes proyectos:

* [Modelo](https://gitlab.semperti.com/hipotecario/poc-fuse-3scale-model)
* [Bup - Mock](https://gitlab.semperti.com/hipotecario/poc-fuse-3scale-bup-mock)
* [Bup - Rest](https://gitlab.semperti.com/hipotecario/poc-fuse-3scale-bup-rest)
* [Cobis - Mock](https://gitlab.semperti.com/hipotecario/poc-fuse-3scale-cobis-mock)
* [Cobis - Rest](https://gitlab.semperti.com/hipotecario/poc-fuse-3scale-cobis-rest)

Además, depende de [Semperti Karaf Blueprint](https://gitlab.semperti.com/semperti/semperti-karaf-blueprint)

## Instalar todo en Openshift

1. Crear Projecto
2. Desplegar [BUP](http://gitlab.semperti.local/hipotecario/poc-fuse-3scale-bup-mock) en openshift:
    1. Mirar readme para como desplegar con comando oc [1]
    2. Setear variables de ambiente para el _deploymennt_: `MYSQL_USER`, `MYSQL_PASSWORD`, `MYSQL_DATABASE` (setear los valores a `bup`)
3. Deplegar [COBIS](http://gitlab.semperti.local/hipotecario/poc-fuse-3scale-cobis-mock) en openshift: Mirar _readme_ [1]
4. Deplegar [Modelo](http://gitlab.semperti.local/hipotecario/poc-3scale-model) en [Nexus](http://nexus.ose3.semperti.com/) (Tener en cuenta que si esto ya fue desplegado no es necesario volver a hacerlo):
    1. Usuario: `admin` Contraseña: `admin123` para servidor id: `nexus-ose-3rdparty` (mirar el _readme_)
    2. `mvn clean deploy`
    3. Chequear si el artefacto se encuentra en [Nexus](http://nexus.ose3.semperti.com/)
4. Deplegar [Semperti karaf blueprint](http://gitlab.semperti.local/semperti/semperti-karaf-blueprint) en [Nexus](http://nexus.ose3.semperti.com/) (Tener en cuenta que si esto ya fue desplegado no es necesario volver a hacerlo):
    1. Usuario: `admin` Contraseña: `admin123` para servidor id: `nexus-ose-3rdparty` (mirar el _readme_)
    2. `mvn clean deploy`
    3. Chequear si el artefacto se encuentra en [Nexus](http://nexus.ose3.semperti.com/)
5. Deplegar [Bup - REST](http://gitlab.semperti.local/hipotecario/poc-fuse-3scale-bup-rest) [1]
   1. Configurar variables de ambiente: `BUP_DATABASE`, `BUP_USER`, `BUP_PASSWORD` (por defecto toman los valores `bup`, pero si se cambiaron en 2.1, setearlas acá)
   2. _Opcional:_ Crear ruta para poder acceder al servicio por fuera de openshift
5. Deplegar [Cobis - REST](http://gitlab.semperti.local/hipotecario/poc-fuse-3scale-cobis-rest) [1]
   1. _Opcional:_ Crear ruta para poder acceder al servicio por fuera de openshift
6. Desplegar [Integrador]((http://gitlab.semperti.local/hipotecario/poc-fuse-3scale) [1]
   1. Crear ruta para poder acceder al servicio por fuera de openshift

[1] Requiere configurar el _secret_ para poder _pull_ear del repositorio de _Gitlab_. Mirar la [wiki](http://gitlab.semperti.local/doc/wiki/tree/master/openshift). El repositorio del integrador contiene las claves ssh en la carpeta `.ssh`.

**Nota:** Tener en cuenta que se estan suponiendo ciertos nombres de _services_:

* Bup: `bup`
* Cobis: `cobis`
* Bup Rest: `bup-rest`
* Cobis Rest: `cobis-rest`
* Integrador: `integrador`

Si se cambian estos nombres de servicio se deben configurar variables de ambiente para que funcione todo correctamente.

# Integrador

Para compilar generando imagen docker:

```
$ mvn clean install docker:build
```

## Configurar

Utiliza las varialbes de ambiente:

* `BUP_SERVICE_HOST`
* `BUP_SERVICE_PORT`
* `ENDPOINT_ADDRESS`
* `COBIS_REST_SERVICE_HOST`
* `COBIS_REST_SERVICE_PORT`
* `BUP_REST_SERVICE_HOST`
* `BUP_REST_SERVICE_PORT`

## Instalar en openshift

Primero hay que subir el template:

```
$ oc project <nombre proyecto>
$ oc create -n `oc project -q` -f ./bup-rest-template.json
```

Despues se puede crear mediante la interfaz web o con el comando `oc new-app ...`

**Nota:** Recordar que esto depende del [modelo](http://gitlab.semperti.local/hipotecario/poc-3scale-model) este debe estar desplegado en _openshift_ para que no falle la compilación.

## Endpoints

```yaml
swagger: '2.0'
info:
  title: API de Personas
  description: API de Personas
  version: 1.0.0
host: integrador-poc-hipotecario-fuse.ose3.semperti.com
schemes:
  - http
basePath: /cxf
produces:
  - application/json
paths:
  /personas:
    get:
      responses:
        200:
          description: Todas las personas
          schema:
            $ref: '#/definitions/Personas'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        401:
          description: Not authorized
          schema:
              $ref: '#/definitions/Error'
        403:
          description: Forbidden
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /personas/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: La persona de id {id}
          schema:
              $ref: '#/definitions/Persona'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        401:
          description: Not authorized
          schema:
              $ref: '#/definitions/Error'
        403:
          description: Forbidden
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'

  /domicilios:
    get:
      responses:
        200:
          description: Todos los domicilios
          schema:
            $ref: '#/definitions/Domicilios'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        401:
          description: Not authorized
          schema:
              $ref: '#/definitions/Error'
        403:
          description: Forbidden
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /domicilios/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: El domicilio de id {id}
          schema:
              $ref: '#/definitions/Domicilio'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        401:
          description: Not authorized
          schema:
              $ref: '#/definitions/Error'
        403:
          description: Forbidden
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /personas/{id}/domicilios:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: Todos los domicilios de la persona con id {id}
          schema:
            $ref: '#/definitions/Domicilios'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'

  /telefonos:
    get:
      responses:
        200:
          description: Todos los telefonos
          schema:
            $ref: '#/definitions/Telefonos'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /telefonos/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: El telefono de id {id}
          schema:
              $ref: '#/definitions/Telefono'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /personas/{id}/telefonos:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: Todos los telefonos de la persona con id {id}
          schema:
            $ref: '#/definitions/Telefonos'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'

  /tarjetas-credito-patrimoniales:
    get:
      responses:
        200:
          description: Todos las tarjetas de credito patrimoniales
          schema:
            $ref: '#/definitions/TarjetasCreditoPatrimoniales'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /tarjetas-credito-patrimoniales/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: La tarjeta de id {id}
          schema:
              $ref: '#/definitions/TarjetaCreditoPatrimonial'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /personas/{id}/tarjetas-credito-patrimoniales:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: Todos las tarjetas de la persona con id {id}
          schema:
            $ref: '#/definitions/TarjetasCreditoPatrimoniales'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /prestamos-patrimoniales:
    get:
      responses:
        200:
          description: Todos los prestamos patrimoniales
          schema:
            $ref: '#/definitions/PrestamosPatrimoniales'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /prestamos--patrimoniales/{id}:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: El prestamo de id {id}
          schema:
              $ref: '#/definitions/PrestamoPatrimonial'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'
  /personas/{id}/prestamos--patrimoniales:
    get:
      parameters:
        - name: id
          in: path
          required: true
          type: number
          format: double
      responses:
        200:
          description: Todos los prestamos de la persona con id {id}
          schema:
            $ref: '#/definitions/PrestamosPatrimoniales'
        400:
          description: Bad Request
          schema:
              $ref: '#/definitions/Error'
        404:
          description: Not found
          schema:
              $ref: '#/definitions/Error'
        500:
          description: Internal Server Error
          schema:
              $ref: '#/definitions/Error'

definitions:
  Personas:
    type: object
    properties:
      telefonos:
        type: array
        description: Lista con todos los telefonos
        items:
          $ref: '#/definitions/Persona'
  Persona:
    type: object
    properties:
      idNumeroDocumento:
        type: integer
        format: int32
        description: Identificador del tipo del documento
      numeroDocumento:
        type: integer
        format: int32
      idSexo:
        type: integer
        format: int32
        description: Identificador del sexo
      id:
        type: integer
        format: int32
        description: Número de tributario
      apellidos:
        type: string
        description: Apellidos de la persona
      nombres:
        type: string
        description: Nombres de la persona
      esPersonaFisica:
        type: boolean
        description: Indica si es una persona física
      esPersonaJuridica:
        type: boolean
        description: Indica si es una persona juridica
      valorLealtadCliente:
        type: string
        description: Score VLC del cliente
      prestamosPatrimoniales:
        type: array
        description: Todos los prestamos de otras entidades declaradas por la persona
        items:
          $ref: '#/definitions/PrestamoPatrimonial'
      tarjetasCreditoPatrimoniales:
        type: array
        description: Todas las tarjetas de credito declaradas por la persona
        items:
          $ref: '#/definitions/TarjetaCreditoPatrimonial'
  Telefonos:
    type: object
    properties:
      telefonos:
        type: array
        description: Lista con todos los telefonos
        items:
          $ref: '#/definitions/Telefono'
  Telefono:
    type: object
    properties:
      id:
        type: integer
        format: int32
        description: Identificador del teléfono
      idTipoTelefono:
        type: integer
        format: int32
        description: Identificador del tipo de teléfono
      codigoPais:
        type: integer
        format: int32
        description: Número que representa el código del pais
      codigoArea:
        type: integer
        format: int32
        description: Número que representa el código de area
      prefijo:
        type: integer
        format: int32
        description: Número que representa el prefijo
      caracteristica:
        type: integer
        format: int32
        description: Número que representa la caracteristica
      numero:
        type: integer
        format: int32
        description: Número completo del telefono
      interno:
        type: integer
        format: int32
        description: Numero que representa al interno
      prioridad:
        type: integer
        format: int32
        description: Orden de prioridad del telefono dentro de los telefonos de la persona
      esListaNegra:
        type: boolean
        description: Indica si el teléfono esta dentro de la lista negra
      numeroNormalizado:
        type: string
        description: Número de teléfono normalizado para el discador
      idPersona:
        type: integer
        format: int32
        description: Relacion con el objeto persona
  Domicilios:
    type: object
    properties:
      telefonos:
        type: array
        description: Lista con todos los Domicilios
        items:
          $ref: '#/definitions/Domicilio'
  Domicilio:
    type: object
    properties:
      id:
        type: integer
        format: int32
        description: Identificador del domicilio
      idTipoDomicilio:
        type: integer
        format: int32
        description: Identificador del tipo del domicilio
      calle:
        type: string
        description: Calle donde se ubica el inmueble
      numero:
        type: integer
        format: int32
        description: Numero de la calle
      piso:
        type: string
        description: Piso del bien en caso de ser propiedad vertical
      departamento:
        type: string
      calleEntre1:
        type: string
        description: Calle 1 entre la que se ubica el inmueble
      calleEntre2:
        type: string
        description: Calle 2 entre la que se ubica el inmueble
      idCodigoPostal:
        type: integer
        format: int32
        description: Identificador del codigo postal
      idCiudad:
        type: integer
        format: int32
        description: Identificador de la ciudad
      idProvincia:
        type: integer
        format: int32
        description: Identificador de la provincia
      idPais:
        type: integer
        format: int32
        description: Identificador del pais
      ubicacion:
        type: string
        description: informacion extra referida a la ubicacion del domicilio
      barrio:
        type: string
        description: Nombre del barrio del domicilio
      latitud:
        type: string
        description: Latitud del domicilio
      longitud:
        type: string
        description: Longitud del domicilio
      idPersona:
        type: integer
        format: int32
        description: Relacion con el objeto persona
  PrestamosPatrimoniales:
    type: object
    properties:
      telefonos:
        type: array
        description: Lista con todos los Prestamos Patrimoniales
        items:
          $ref: '#/definitions/PrestamoPatrimonial'
  PrestamoPatrimonial:
    type: object
    properties:
      id:
        type: integer
        format: int32
        description: Identificador del prestamos patrimonial
      idTipoPrestamo:
        type: integer
        format: int32
        description: Identificador del tipo de prestamo
      idEntidad:
        type: integer
        format: int32
        description: Identificador de la entidad prestadora
      deuda:
        type: number
        format: float
        description: Monto de la deuda
      plazoRestante:
        type: integer
        format: int32
        description: Plazo restante
      valorCuota:
        type: number
        format: float
        description: Valor de la cuota
      esACancelar:
        type: boolean
        description: Si el prestamo se va cancelar
      idPersona:
        type: integer
        format: int32
        description: Relacion con el objeto persona
  TarjetasCreditoPatrimoniales:
    type: object
    properties:
      telefonos:
        type: array
        description: Lista con todos las Tarjetas de Credito Patrimoniales
        items:
          $ref: '#/definitions/TarjetaCreditoPatrimonial'
  TarjetaCreditoPatrimonial:
    type: object
    properties:
      id:
        type: integer
        format: int32
        description: identificador de la tarjeta de credito patrimonial
      idMarcaTCPatrimonial:
        type: integer
        format: int32
        description: Marca de la tarjeta (VISA, CABAL, MASTERCARD, etc)
      idEntidad:
        type: integer
        format: int32
        description: Entidad de la tarjerta
      limiteCompra:
        type: number
        format: float
        description: Límite de compra
      deuda:
        type: number
        format: float
        description: Deuda
      pagoPromedio:
        type: number
        format: float
        description: Pago promedio
      idPersona:
        type: integer
        format: int32
        description: Relacion con el objeto persona
  Error:
    type: object
    properties:
      codigo:
        type: integer
        format: int32
      mensajeAlDesarrollador:
        type: string
      mensajeAlUsuario:
        type: string
      detalle:
        type: string
      masInformacion:
        type: string

```


