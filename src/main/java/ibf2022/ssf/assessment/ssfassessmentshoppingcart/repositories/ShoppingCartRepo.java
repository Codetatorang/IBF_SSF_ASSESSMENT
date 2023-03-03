package ibf2022.ssf.assessment.ssfassessmentshoppingcart.repositories;

import java.util.HashMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.ssf.assessment.ssfassessmentshoppingcart.models.Item;

@Repository
public class ShoppingCartRepo {
    private Logger logger = Logger.getLogger(ShoppingCartRepo.class.getName());

    @Autowired
    @Qualifier("shoppingCart")
    private RedisTemplate<String, String> redisTemplate;

    private HashMap<String, Item> itemMap;

    private Boolean isEmptyMap = true;

    public Boolean isEmptyMap() {
        // logging
        logger.info("map size: %d".formatted(itemMap.size()));

        if (itemMap.size() > 0)
            isEmptyMap = false;
        isEmptyMap = true;

        return isEmptyMap;
    }

    public ShoppingCartRepo() {
        if (itemMap == null)
            itemMap = new HashMap<String, Item>();
    }

    // list cart items
    public HashMap<String, Item> cartItemMap() {
        return itemMap;
    }

    // add item to cart
    public String addToCart(Item item) {
        itemMap.put(item.getItem(), item);
        return "sucessfully added %s of %d quantity.".formatted(item.getItem(), item.getQuantity());
    }

    // get cart item
    public Item getCartItem(Item item) {
        Item itm = itemMap.get(item.getItem());
        return itm;
    }
}
