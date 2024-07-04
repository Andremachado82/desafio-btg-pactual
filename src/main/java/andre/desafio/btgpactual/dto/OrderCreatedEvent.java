package andre.desafio.btgpactual.dto;

import java.util.List;

public record OrderCreatedEvent(Long codigoPeido,
                                Long codigoCliente,
                                List<OrderItemEvent> itens) {
}
