package org.launchcode.stocks.models.service;

import org.launchcode.stocks.models.StockHolding;
import org.launchcode.stocks.models.User;

/**
 * Created by jmurapal on 4/4/2020.
 */
public interface UserService {
    void addHolding (StockHolding holding, User user) throws IllegalArgumentException;

}
