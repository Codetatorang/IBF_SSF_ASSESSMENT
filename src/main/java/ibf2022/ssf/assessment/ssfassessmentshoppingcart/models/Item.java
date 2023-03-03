package ibf2022.ssf.assessment.ssfassessmentshoppingcart.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Item {
    @NotNull(message="Cannot choose nothing.")
    private String item;

    @Min(value=1, message="Minimum stock order is 1.")
    private Integer quantity;
    
    public String getItem() {return item;}
    public void setItem(String item) {this.item = item;}

    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
   
}
