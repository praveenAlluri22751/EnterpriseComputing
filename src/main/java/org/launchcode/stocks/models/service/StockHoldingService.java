package org.launchcode.stocks.models.service;

import org.launchcode.stocks.models.StockHolding;
import org.launchcode.stocks.models.StockLookupException;
import org.launchcode.stocks.models.User;

import java.util.List;

/**
 * Created by jmurapal on 4/4/2020.
 */
public interface StockHoldingService {
    StockHolding buyShares(User user, String symbol, int numberOfShares, float price) throws StockLookupException;

    StockHolding sellShares(User user, String symbol, int numberOfShares, float price) throws StockLookupException;

    //void updateShares(StockHolding holding, int numberOfShares, float price) throws StockLookupException;

    List<StockHolding> getAllShares(User user);

}
