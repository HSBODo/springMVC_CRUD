package pointman.itemservice.domain.item;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data //핵심 도메인 모델에 사용하기 위험함 예측하지 못하게 동작할 경우도 있음 getter setter만 쓰도록하자
//DTO에는 @Data 사용해도 괜찮다
//@Getter @Setter
@Data //공부하기 위해서 일단은 사용해보고 어떤 경우가 위험한지 파악해본다
public class Item {
    private Long id;
    private String itemName;
    private Integer price; //Integer사용 이유는 가격이 없을 경우(null)를 대비해서 int같은 기본 자료형은 null값을 받지 못함
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
