package kitchenpos.application;

import java.util.List;
import kitchenpos.ServiceTest;
import kitchenpos.application.CreateTableGroupCommand.TableInGroup;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderRepository;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.OrderTableRepository;
import kitchenpos.domain.TableGroup;
import kitchenpos.domain.TableGroupRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kitchenpos.OrderLineItemFixture.id_없는_주문항목;
import static kitchenpos.domain.OrderStatus.COMPLETION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ServiceTest
class TableGroupServiceTest {

    @Autowired
    private TableGroupService tableGroupService;

    @Autowired
    private TableGroupRepository tableGroupRepository;

    @Autowired
    private OrderTableRepository orderTableRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Nested
    class 테이블_그룹_생성 {

        @Test
        void 테이블_그룹을_생성할_수_있다() {
            //given
            Long 아이디1 = 빈_테이블_생성().getId();
            Long 아이디2 = 빈_테이블_생성().getId();
            CreateTableGroupCommand 커맨드 = new CreateTableGroupCommand(
                    List.of(new TableInGroup(아이디1), new TableInGroup(아이디2)));

            //when
            TableGroupDto 생성된_테이블그룹 = tableGroupService.create(커맨드);

            //then
            TableGroup 조회 = tableGroupRepository.getById(생성된_테이블그룹.getId());
            assertAll(
                    () -> assertThat(조회.getOrderTables())
                            .extracting("id")
                            .contains(아이디1, 아이디2)
            );

        }

        @Test
        void 테이블이_존재하지_않으면_예외가_발생한다() {
            //given
            TableInGroup 존재하지_않는_테이블 = new TableInGroup(null);
            CreateTableGroupCommand 커맨드 = new CreateTableGroupCommand(
                    List.of(new TableInGroup(1L), 존재하지_않는_테이블));

            //expect
            assertThatThrownBy(() -> tableGroupService.create(커맨드))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 이미_그룹에_속한_테이블이_있으면_에외가_발생한다() {
            //given
            OrderTable 테이블 = 그룹에_속한_테이블_생성();
            CreateTableGroupCommand 커맨드 = new CreateTableGroupCommand(
                    List.of(new TableInGroup(테이블.getId()), new TableInGroup(빈_테이블_생성().getId()))
            );

            //expect
            assertThatThrownBy(() -> tableGroupService.create(커맨드))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        private OrderTable 그룹에_속한_테이블_생성() {
            OrderTable 테이블 = 빈_테이블_생성();
            CreateTableGroupCommand 커맨드 = new CreateTableGroupCommand(
                    List.of(new TableInGroup(테이블.getId()), new TableInGroup(빈_테이블_생성().getId()))
            );

            tableGroupService.create(커맨드);
            return orderTableRepository.getById(테이블.getId());
        }

    }

    private OrderTable 빈_테이블_생성() {
        final var 테이블 = new OrderTable(0, true);
        return orderTableRepository.save(테이블);
    }

    @Nested
    class 테이블_그룹_해제 {

        @Test
        void 성공() {
            //given
            OrderTable 테이블 = 빈_테이블_생성();
            Order 주문 = new Order(null, 테이블.getId(), COMPLETION, null, List.of(id_없는_주문항목()));
            orderRepository.save(주문);

            TableGroup 테이블_그룹 = new TableGroup();
            TableGroup 생성된_테이블_그룹 = tableGroupRepository.save(테이블_그룹);
            생성된_테이블_그룹.changeOrderTables(List.of(테이블, 빈_테이블_생성()));

            //when
            tableGroupService.ungroup(생성된_테이블_그룹.getId());

            //then
            assertThat(orderTableRepository.getById(테이블.getId()).getTableGroupId()).isNull();
        }

    }

}
