package pointman.itemservice.web.basic;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pointman.itemservice.domain.item.Item;
import pointman.itemservice.domain.item.ItemRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
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

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";

    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId,@ModelAttribute Item item,Model model){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping("/{itemId}/delete")
    public String delete(@PathVariable long itemId){
        log.info("delete");
        itemRepository.delete(itemId);
        return "redirect:/basic/items";
    }


    @GetMapping("/add")
    public String adForm(){
        return "basic/addForm";

    }
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,@RequestParam int price, @RequestParam Integer quantity, Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";

    }

    /**
     * save() @ModelAttribute 방식
     * req 파라미터와 Item 객체어 프로퍼티 이름이 같으면 자동으로 set 해준다
     */

   // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);
       // model.addAttribute("item",item); //@ModelAttribute("item")  이름을 선언하면 model에 item key 에 value를 넣어준다
        return "basic/item";

    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){
        itemRepository.save(item);
        // model.addAttribute("item",item); //@ModelAttribute  이름을 선언부분까지 생략하면 class이름(Item)의 첫글자를 소문자로 변경(item)뒤에  model에 item key 에 value를 넣어준다
        return "basic/item";

    }

    //@PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        // model.addAttribute("item",item); //@ModelAttribute 생략하면 class이름(Item)의 첫글자를 소문자로 변경(item)뒤에  model에 item key 에 value를 넣어준다
        return "basic/item";

    }

    /**
     * PRG - Post/Redirect/Get
     */

    //@PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();

    }

    /**
     * RedirectAttribute
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true); //쿼리 파라미터러 넘어감
        return "redirect:/basic/items/{itemId}";
        //http://localhost:8088/basic/items/3?status=true
    }
    @PostConstruct //의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다.  bean 의 생애주기에서 오직 한 번만 수행된다는 것을 보장한다.
    public void init(){
        itemRepository.save(new Item("itemA",11000,10));
        itemRepository.save(new Item("itemB",22000,20));
    }
}
