# 키친포스

## 요구 사항

- [x] 주문을 생성할 수 있다.
  - [x] 주문시 테이블 정보가 필요하다.
    - [x] 주문하는 테이블이 존재하지 않는 경우 예외가 발생한다.
  - [x] 주문에 포함된 상품이 없으면 예외가 발생한다.
  - [x] 주문에 같은 상품이 여러 개의 주문 상품으로 있을 경우 예외가 발생한다.
  - [x] 주문 생성 시 주문 상품(OrderLineItem)들을 함께 생성한다.   
- [x] 주문 리스트를 조회할 수 있다.
- [ ] 주문 상태를 변경할 수 있다.
  - [ ] 주문을 찾을 수 없는 경우 예외가 발생한다.
  - [ ] 주문 상태가 COMPLETION인 경우 예외가 발생한다.


- [x] 상품을 생성할 수 있다.
  - [x] 상품의 가격이 null이거나 음수인 경우 예외가 발생한다.
- [x] 상품 리스트를 조회할 수 있다.


- [x] 메뉴 그룹을 생성할 수 있다.
- [x] 메뉴 그룹 리스트를 조회할 수 있다.


- [x] 메뉴를 생성할 수 있다.
  - [x] 가격은 null이거나 음수일 수 없다. 
  - [x] 생성 시 메뉴가 속할 그룹의 정보가 필요하다.
  - [x] 메뉴가 속할 그룹이 존재하지 않으면 예외가 발생한다.
  - [x] 메뉴에 속하는 상품이 존재하지 않으면 예외가 발생한다.
  - [x] 상품들의 가격의 합과 메뉴 가격이 동일하지 않은 경우 예외가 발생한다.
- [x] 메뉴 리스트를 조회할 수 있다.


- [ ] 테이블 그룹을 생성할 수 있다.
  - [ ] 테이블 그룹에는 2개 이상의 테이블이 포함되어야 한다.
  - [ ] 테이블 그룹에 속하는 테이블이 존재하지 않으면 예외가 발생한다.
  - [ ] 테이블에 주문이 있거나 이미 속하는 테이블 그룹이 있으면 예외가 발생한다.
- [ ] 테이블 그룹을 해제할 수 있다.
  - [ ] 테이블 그룹에 속한 테이블 중 COOKING 또는 MEAL 상태의 주문이 있는 경우 예외가 발생한다.
- [ ] 테이블 그룹 리스트를 조회할 수 있다.


- [x] 테이블의 주문을 생성할 수 있다.
- [x] 테이블 리스트를 조회할 수 있다.
- [ ] 빈테이블로 만들 수 있다.
  - [ ] 테이블에 COOKING 또는 MEAL 상태의 주문이 있는 경우 예외가 발생한다.
  - [ ] 테이블이 속한 그룹이 있는 경우 예외가 발생한다.
  - [ ] 빈테이블은 주문을 등록할 수 없다.
- [x] 테이블의 인원을 변경할 수 있다.
  - [x] 인원이 음수이면 예외가 발생한다.
  - [x] 변경하려는 테이블이 존재하지 않으면 예외가 발생한다.
  - [x] 변경하려는 테이블이 빈테이블이면 예외가 발생한다.

## 용어 사전

| 한글명      | 영문명              | 설명                            |
|----------|------------------|-------------------------------|
| 상품       | product          | 메뉴를 관리하는 기준이 되는 데이터           |
| 메뉴 그룹    | menu group       | 메뉴 묶음, 분류                     |
| 메뉴       | menu             | 메뉴 그룹에 속하는 실제 주문 가능 단위        |
| 메뉴 상품    | menu product     | 메뉴에 속하는 수량이 있는 상품             |
| 금액       | amount           | 가격 * 수량                       |
| 주문 테이블   | order table      | 매장에서 주문이 발생하는 영역              |
| 빈 테이블    | empty table      | 주문을 등록할 수 없는 주문 테이블           |
| 주문       | order            | 매장에서 발생하는 주문                  |
| 주문 상태    | order status     | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정    | table group      | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목    | order line item  | 주문에 속하는 수량이 있는 메뉴             |
| 매장 식사    | eat in           | 포장하지 않고 매장에서 식사하는 것           |
