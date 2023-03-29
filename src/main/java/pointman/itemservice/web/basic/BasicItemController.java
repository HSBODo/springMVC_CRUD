package pointman.itemservice.web.basic;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pointman.itemservice.domain.item.Item;
import pointman.itemservice.domain.item.ItemRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired 생성자가 하나만 있으면 생략하여도 의존성 주입가능
//    public BasicItemController(ItemRepository itemRepository) {  @RequiredArgsConstructor 애노테이션을 사용하여 final이 붙은 것으로 생성자를 만들어준다  그리하여 생성자 생략 가능
//        this.itemRepository = itemRepository; //생성자 주입  ItemRepository는 @Repository 애노테이션을 사용하여 빈등록
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items =itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";

    }

    @PostConstruct //의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다.  bean 의 생애주기에서 오직 한 번만 수행된다는 것을 보장한다.
    public void init(){
        itemRepository.save(new Item("itemA",11000,10));
        itemRepository.save(new Item("itemB",22000,20));
    }
}
