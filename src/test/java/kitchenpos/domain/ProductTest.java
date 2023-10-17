package kitchenpos.domain;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {
    @Test
    void 가격과_이름으로_생성할_수_있다() {
        //given
        String name = "후라이드";
        BigDecimal price = BigDecimal.valueOf(16000);

        //when
        final var product = new Product(name, price);

        //then
        assertAll(
                () -> assertThat(product).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }

    @Test
    void name이_null이면_예외가_발생한다() {
        //given
        String name = null;
        BigDecimal price = BigDecimal.valueOf(16000);

        //expect
        assertThatThrownBy(() -> new Product(name, price))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void price가_null이면_예외가_발생한다() {
        //given
        String name = "후라이드";
        BigDecimal price = null;

        //expect
        assertThatThrownBy(() -> new Product(name, price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void price가_음수이면_예외가_발생한다() {
        //given
        String name = "후라이드";
        BigDecimal price = BigDecimal.valueOf(-16000);

        //expect
        assertThatThrownBy(() -> new Product(name, price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}