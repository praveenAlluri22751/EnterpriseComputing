package org.launchcode.stocks.models.service.impl;

import org.launchcode.stocks.models.StockHolding;
import org.launchcode.stocks.models.StockLookupException;
import org.launchcode.stocks.models.StockTransaction;
import org.launchcode.stocks.models.User;
import org.launchcode.stocks.models.dao.StockHoldingDao;
import org.launchcode.stocks.models.service.StockHoldingService;
import org.launchcode.stocks.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jmurapal on 4/4/2020.
 */
@Service
public class StockHoldingServiceImpl implements StockHoldingService {

    @Autowired
    UserService userService;

    @Autowired
    StockHoldingDao stockHoldingDao;

    /**
     * Static method for buying shares of a StockHolding. Creates a new holding if the user did not already have one,
     * otherwise simply updates sharesOwned on the existing holding
     *
     * @param user              user to buy the stock
     * @param symbol            symbol of the stock to buy
     * @param numberOfShares    number of shares to buy
     * @return                  the holding for the user and symbol
     * @throws IllegalArgumentException
     */
    public StockHolding buyShares(User user, String symbol, int numberOfShares, float price) throws StockLookupException {

        // TODO - make sure symbol matches case convention

        // Get existing holding
        Map<String, StockHolding> userPortfolio = user.getPortfolio();
        StockHolding holding;

        // Create new holding, if user has never owned the stock before
        if (!userPortfolio.containsKey(symbol)) {
            holding = new StockHolding();
            holding.setOwnerId(user.getUid());
            holding.setSymbol(symbol.toUpperCase());
            holding.setSharesOwned(numberOfShares);
            StockTransaction transaction = new StockTransaction(holding, numberOfShares, StockTransaction.TransactionType.BUY, price);
            holding.getTransactions().add(transaction);
            userService.addHolding(holding, user);
            //stockHoldingDao.save(holding);
        } else {
            // Conduct buy
            holding = userPortfolio.get(symbol);
            updateShares(holding, numberOfShares, price, StockTransaction.TransactionType.BUY);
        }
        return holding;
    }

    /**
     * Static method for buying shares of a StockHolding. Creates a new holding if the user did not already have one,
     * otherwise simply updates sharesOwned on the existing holding
     *
     * @param user              user to buy the stock
     * @param symbol            symbol of the stock to buy
     * @param numberOfShares    number of shares to buy
     * @return                  the holding for the user and symbol
     * @throws IllegalArgumentException
     */
    public StockHolding sellShares(User user, String symbol, int numberOfShares, float price) throws StockLookupException {

        // TODO - make sure symbol matches case convention

        // Get existing holding
        Map<String, StockHolding> userPortfolio = user.getPortfolio();
        StockHolding holding;

        // Create new holding, if user has never owned the stock before
        if (!userPortfolio.containsKey(symbol)) {
            return null;
        } else {
            // Conduct buy
            holding = userPortfolio.get(symbol);
            updateShares(holding, numberOfShares, price, StockTransaction.TransactionType.SELL);
        }
        return holding;
    }


    /**
     * Instance method for buying shares of a holding
     *
     * @param numberOfShares
     * @throws IllegalArgumentException if numberOfShares < 0
     * @throws StockLookupException     if unable to lookup stock info
     */
    private void updateShares(StockHolding holding, int numberOfShares, float price, StockTransaction.TransactionType type) throws StockLookupException {
        if (numberOfShares < 0) {
            throw new IllegalArgumentException("Can not purchase a negative number of shares.");
        }
        StockTransaction transaction = null;
        if (type == StockTransaction.TransactionType.BUY) {
            holding.setSharesOwned((holding.getSharesOwned() + numberOfShares));
            transaction = new StockTransaction(holding, numberOfShares, StockTransaction.TransactionType.BUY, price);
        } else if (type == StockTransaction.TransactionType.SELL) {
            holding.setSharesOwned((holding.getSharesOwned() - numberOfShares));
            transaction = new StockTransaction(holding, numberOfShares, StockTransaction.TransactionType.SELL, price);
        }
        if(transaction != null) {
            holding.getTransactions().add(transaction);
            stockHoldingDao.save(holding);
        }
    }

    public List<StockHolding> getAllShares(User user) {
        return stockHoldingDao.findByOwnerId(user.getUid());
    }
}
