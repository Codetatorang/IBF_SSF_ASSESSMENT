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
    public String postView1(HttpSession session, @Valid Item item, BindingResult bResult, Model model) {
        // logging
        logger.info("GET /: %s".formatted(item.toString()));

        // session.invalidate();

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
        session.setAttribute("item", item);

        model.addAttribute("item", new Item());
        model.addAttribute("svc", shoppingcartSvc);
        logger.info("%s".formatted(shoppingcartSvc));
        return "view1";
    }

    @GetMapping(path = { "/view2" })
    public String deliveryPage(HttpSession session, Model model){
        model.addAttribute("item", new Item());
        return "view2";
    }
}
