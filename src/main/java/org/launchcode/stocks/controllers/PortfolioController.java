package org.launchcode.stocks.controllers;

import org.launchcode.stocks.models.StockHolding;
import org.launchcode.stocks.models.User;
import org.launchcode.stocks.models.service.StockHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Chris Bay on 5/17/15.
 */
@Controller
public class PortfolioController extends AbstractController {

    @Autowired
    StockHoldingService stockHoldingService;

    @RequestMapping(value = "/portfolio")
    public String portfolio(HttpServletRequest request, Model model){

        // TODO - Implement portfolio display

        User user = getUserFromSession(request);

        List<StockHolding> stockHoldings = stockHoldingService.getAllShares(user);

        model.addAttribute("title", "Portfolio");
        model.addAttribute("portfolioNavClass", "active");

        model.addAttribute("listOfShares", stockHoldings);

        return "portfolio";
    }

    /*@RequestMapping(value = "/portfolio")
    public ModelAndView portfolio(HttpServletRequest request, Model model){

        // TODO - Implement portfolio display
        ModelAndView modelAndView = new ModelAndView("portfolio");

        model.addAttribute("title", "Portfolio");
        model.addAttribute("portfolioNavClass", "active");

        modelAndView.addObject("title", "Portfolio");
        return modelAndView;
    }*/

}
