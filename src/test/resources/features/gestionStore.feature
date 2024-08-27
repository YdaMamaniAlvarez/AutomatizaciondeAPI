Feature: Gestión de pedidos en la tienda de mascotas
  @crearPedidodeMascota
  Scenario Outline: Crear un nuevo pedido de mascota
  Given que tengo los detalles del pedido de una mascota
  | id   | petId   | quantity   | status   |
  | <id> | <petId> | <quantity> | <status> |
  When realizo una solicitud POST a "/store/order" con los detalles del pedido
  Then la respuesta debe tener un código de estado "200"
  And la respuesta debe contener los detalles del pedido creado
  And el estado del pedido debe ser "<status>"

  Examples:
  | id | petId | quantity | status    |
  | 1  | 1     | 1        | placed    |
  | 2  | 2     | 1        | approved  |
  | 3  | 3     | 1        | delivered |

  @consultarPedidoporId
  Scenario Outline: Obtener un pedido existente por ID
  Given que existe un pedido con ID "<orderId>" en el sistema
  When realizo una solicitud GET a "/store/order/<orderId>"
  Then la respuesta debe tener un código de estado "200"
  And la respuesta debe contener los detalles del pedido con ID "<orderId>"

  Examples:
  | orderId |
  | 1       |
  | 2       |
  | 3       |

