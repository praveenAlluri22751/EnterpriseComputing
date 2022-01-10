package org.launchcode.stocks.controllers;

import org.launchcode.stocks.models.StockHolding;
import org.launchcode.stocks.models.User;
import org.launchcode.stocks.models.dao.StockHoldingDao;
import org.launchcode.stocks.models.service.StockHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Chris Bay on 5/17/15.
 */
@Controller
public class StockController extends AbstractController {

    @Autowired
    StockHoldingService stockHoldingService;

    @RequestMapping(value = "/quote", method = RequestMethod.GET)
    public String quoteForm(Model model) {

        // pass data to template
        model.addAttribute("title", "Quote");
        model.addAttribute("quoteNavClass", "active");
        return "quote_form";
    }

    @RequestMapping(value = "/quote", method = RequestMethod.POST)
    public String quote(String symbol, Model model) {

        // TODO - Implement quote lookup

        // pass data to template
        model.addAttribute("title", "Quote");
        model.addAttribute("quoteNavClass", "active");

        return "quote_display";
    }

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    public String buyForm(Model model) {

        model.addAttribute("title", "Buy");
        model.addAttribute("action", "/buy");
        model.addAttribute("buyNavClass", "active");
        return "transaction_form";
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buy(String symbol, int numberOfShares, float price, HttpServletRequest request, Model model) {

        // TODO - Implement buy action
        User user = getUserFromSession(request);

        model.addAttribute("title", "Buy");
        model.addAttribute("action", "/buy");
        model.addAttribute("buyNavClass", "active");
        try {
            stockHoldingService.buyShares(user, symbol, numberOfShares, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "transaction_confirm";
    }

    @RequestMapping(value = "/sell", method = RequestMethod.GET)
    public String sellForm(Model model) {
        model.addAttribute("title", "Sell");
        model.addAttribute("action", "/sell");
        model.addAttribute("sellNavClass", "active");
        return "transaction_form";
    }

    @RequestMapping(value = "/sell", method = RequestMethod.POST)
    public String sell(String symbol, int numberOfShares, float price, HttpServletRequest request, Model model) {

        // TODO - Implement Sell action
        User user = getUserFromSession(request);

        model.addAttribute("title", "Sell");
        model.addAttribute("action", "/sell");
        model.addAttribute("sellNavClass", "active");

        try {
            StockHolding stockHolding = stockHoldingService.sellShares(user, symbol, numberOfShares, price);
            if (stockHolding == null) {
                model.addAttribute("errorMsg", "No Shares available with the given Symbol");
                return "transaction_form";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "transaction_confirm";
    }

}
