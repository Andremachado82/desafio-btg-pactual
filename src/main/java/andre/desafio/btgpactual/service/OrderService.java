package andre.desafio.btgpactual.service;

import andre.desafio.btgpactual.dto.OrderCreatedEvent;
import andre.desafio.btgpactual.entity.OrderEntity;
import andre.desafio.btgpactual.entity.OrderItem;
import andre.desafio.btgpactual.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void save(OrderCreatedEvent event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());

        entity.setItems(getItems(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getItems(OrderCreatedEvent event) {
       return event.itens().stream()
                        .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                        .toList();
    }
}
