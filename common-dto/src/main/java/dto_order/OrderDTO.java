package dto_order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID orderId;
    private String orderCreatedDate;
    private BigDecimal orderPrice;
    private boolean paidFor;
    private boolean packed;
    private boolean shipped;

    @Override
    public String toString() {
        return "Id: " + orderId + "\n" +
                "Дата создания: " + orderCreatedDate + "\n" +
                "Цена заказа: " + orderPrice;
    }
}
