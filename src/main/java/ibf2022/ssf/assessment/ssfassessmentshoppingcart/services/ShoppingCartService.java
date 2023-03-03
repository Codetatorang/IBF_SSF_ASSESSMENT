package ibf2022.ssf.assessment.ssfassessmentshoppingcart.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import ibf2022.ssf.assessment.ssfassessmentshoppingcart.models.Item;
import ibf2022.ssf.assessment.ssfassessmentshoppingcart.repositories.ShoppingCartRepo;

@Service
public class ShoppingCartService {
  @Autowired
  private ShoppingCartRepo shoppingcartRepo;
  
    // for validation purposes
    public static final String[] ITEM_NAMES = {
            "Apple", "Orange", "Bread", "Cheese", "Chicken", "Mineral Water", "Instant Noodles"
    };

    private final Set<String> optionNames;

    public ShoppingCartService() {
		optionNames = new HashSet<>(Arrays.asList(ITEM_NAMES));
	}

    //retrieve cart items
    public void listCartItems(){
      shoppingcartRepo.cartItemMap();
    }

    //check empty map
    public Boolean isEmpty(){
      return shoppingcartRepo.isEmptyMap();
    }
    
    //check item in cart 
    public Boolean checkItemsInCart(Item item){
      if(shoppingcartRepo.cartItemMap().containsKey(item.getItem())){
        return true;
      }
      return false;
    }
    //add to cart
    public String addItemsToCart(Item item){

      Item itm;
      
      if(checkItemsInCart(item)){
        itm = shoppingcartRepo.getCartItem(item);
        itm.setQuantity(itm.getQuantity() + item.getQuantity());
      }
      else{
        shoppingcartRepo.addToCart(item);
        itm = item;
      }
      
      return "successfully added %d to %s. Total quantity: %d".formatted(item.getQuantity(),item.getItem(), itm.getQuantity());
    }

    //validation check
    public List<ObjectError> validateCartItem(Item cart) {
		List<ObjectError> errors = new LinkedList<>();
		FieldError error;

		if (!optionNames.contains(cart.getItem().toLowerCase())) {
			error = new FieldError("item", "item"
					, "We do not have %s.".formatted(cart.getItem()));
			errors.add(error);
		}

		return errors;
	}
}
