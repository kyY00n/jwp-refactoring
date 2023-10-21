package kitchenpos.domain.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import kitchenpos.domain.Money;
import kitchenpos.domain.product.Product;

import static java.util.Objects.requireNonNull;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Money price;
    private Long menuGroupId;

    @OneToMany(mappedBy = "menu", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected Menu() {
    }

    public static Menu of(final String name, final Money price, final Long menuGroupId,
                          final Map<Product, Integer> productToQuantity) {
        Menu menu = new Menu(null, name, price, menuGroupId);
        menu.changeMenuProducts(productToQuantity);
        return menu;
    }

    public Menu(final Long id, final String name, final Money price, final Long menuGroupId) {
        this.id = id;
        this.name = requireNonNull(name, "메뉴 이름은 null일 수 없습니다.");
        this.price = requireNonNull(price, "메뉴 가격은 null일 수 없습니다.");
        this.menuGroupId = menuGroupId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void changeMenuProducts(final Map<Product, Integer> productToQuantity) {
        final var sumOfMenuProductPrice = productToQuantity.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().times(entry.getValue()))
                .reduce(Money.ZERO, Money::plus);
        if (price.isHigherThan(sumOfMenuProductPrice)) {
            throw new IllegalArgumentException("메뉴 가격이 메뉴에 속한 상품 가격의 합보다 큽니다.");
        }
        final List<MenuProduct> menuProducts = productToQuantity.entrySet().stream()
                .map(entry -> new MenuProduct(null, this, entry.getKey().getId(), entry.getValue()))
                .collect(Collectors.toList());
        this.menuProducts = menuProducts;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
