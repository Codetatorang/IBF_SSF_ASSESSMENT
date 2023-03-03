package ibf2022.ssf.assessment.ssfassessmentshoppingcart.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Delivery {
    @NotNull(message="Please provide us with a name.")
    @Size(min=2,message="Please provide at least a name with 2 characters.")
    private String name;
    @NotNull(message="Please provide us with your delivery address.")
    private String address;
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    
}
