package org.launchcode.stocks.models.service.impl;

import org.launchcode.stocks.models.StockHolding;
import org.launchcode.stocks.models.User;
import org.launchcode.stocks.models.dao.UserDao;
import org.launchcode.stocks.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jmurapal on 4/4/2020.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public void addHolding (StockHolding holding, User user) throws IllegalArgumentException {

        // Ensure a holding for the symbol doesn't already exist
        if (user.getPortfolio().containsKey(holding.getSymbol())) {
            throw new IllegalArgumentException("A holding for symbol " + holding.getSymbol()
                    + " already exits for user " + user.getUid());
        }
        user.getPortfolio().put(holding.getSymbol(), holding);
        userDao.save(user);
    }
}
