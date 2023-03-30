package pointman.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private static final Map<Long,Item> store = new HashMap<>(); //실제로는 멀티스레드 환경에서 동시에 여러개의 생성이 있을때 싱글톤으로 생성되기 떄문에 동시성 문제로 HashMap 사용하면 안된다 실무에서는 ConcurrentHashMap<> 사용
    private static  long sequence = 0L;

    public  Item save (Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void delete(Long itemId){
        store.remove(itemId);
    }
    public void clearStore(){
        store.clear();
    }

}
