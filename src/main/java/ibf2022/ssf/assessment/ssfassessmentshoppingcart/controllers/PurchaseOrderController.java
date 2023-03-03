package ibf2022.ssf.assessment.ssfassessmentshoppingcart.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ibf2022.ssf.assessment.ssfassessmentshoppingcart.models.Delivery;
import ibf2022.ssf.assessment.ssfassessmentshoppingcart.models.Item;
import ibf2022.ssf.assessment.ssfassessmentshoppingcart.services.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PurchaseOrderController {
    @Autowired
    private ShoppingCartService shoppingcartSvc;

    private Logger logger = Logger.getLogger(PurchaseOrderController.class.getName());

    
    @GetMapping(path = { "/" })
    public String landingPage(HttpSession session, Model model){
        session.invalidate();
        model.addAttribute("item", new Item());
        return "view1";
    }

    @PostMapping(path = { "/view1" })
    public String postView1(HttpSession session, Model model,@Valid Item item, BindingResult bResult ) {
        // logging
        logger.info("GET /: %s".formatted(item.toString()));

        // return to form and report errors
        if (bResult.hasErrors()) {
            return "view1";
        }

        List<ObjectError> errors = shoppingcartSvc.validateCartItem(item);
        if (!errors.isEmpty()) {
            for (ObjectError err : errors)
                bResult.addError(err);
            return "view1";
        }

        Item itm = (Item) session.getAttribute("item");
        ShoppingCartService itmSvc = (ShoppingCartService) session.getAttribute("svc");
        //set the http session
        if(null == itm){
            itm = new Item();
            session.setAttribute("item", itm);
        }
        if(null == itmSvc){
            itmSvc = new ShoppingCartService();
            session.setAttribute("svc", itmSvc);
        }

        model.addAttribute("item", new Item());
        model.addAttribute("svc", shoppingcartSvc);
        logger.info("%s".formatted(shoppingcartSvc));
        return "view1";
    }

    @PostMapping(path = { "/view2" })
    public String deliveryPage(HttpSession session, @Valid Delivery delivery, BindingResult bResult, Model model){

        //get current session
        Delivery deli = (Delivery)session.getAttribute("delivery");
        //customer attempts to come here without a cart!
        if(null == deli){
            deli = new Delivery();
            session.setAttribute("delivery", deli);
        }

        //errors detected
        if (bResult.hasErrors()) {
            return "view2";
        }

        // List<ObjectError> errors = shoppingcartSvc.validateCartItem(item);
        // if (!errors.isEmpty()) {
        //     for (ObjectError err : errors)
        //         bResult.addError(err);
        //     return "view2";
        // }


        model.addAttribute("delivery", new Delivery());
        return "view2";
    }

    @PostMapping(path={"/view3"})
    public String invoicePage(HttpSession session, Model model){
        logger.info("code reached here.");
        return "";
    }

    @GetMapping(path="/shippingaddress")
    public String shippingAddress(HttpSession session, Model model){
        logger.info("Entered shipping address");
        return "view3";
    }
}
