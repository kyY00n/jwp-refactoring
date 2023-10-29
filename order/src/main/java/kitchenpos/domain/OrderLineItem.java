package kitchenpos.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kitchenpos.Money;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Embedded
    private MenuVo menu;

    private long quantity;

    protected OrderLineItem() {
    }

    public static OrderLineItem of(final Long seq, final Long menuId, final String menuName, final Money menuPrice,
                                   final long quantity) {
        return new OrderLineItem(seq, new MenuVo(menuId, menuName, menuPrice), quantity);
    }

    public OrderLineItem(final Long seq, final MenuVo menu, final long quantity) {
        this.seq = seq;
        this.menu = menu;
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getMenuId() {
        return menu.getId();
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderLineItem that = (OrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

}
