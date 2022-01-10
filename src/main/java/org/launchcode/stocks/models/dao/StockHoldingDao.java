package org.launchcode.stocks.models.dao;

import org.launchcode.stocks.models.StockHolding;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Chris Bay on 5/17/15.
 */
@Transactional
@Repository
public interface StockHoldingDao extends CrudRepository<StockHolding, Integer> {

    StockHolding findBySymbolAndOwnerId(String symbol, int ownerId);

    @Query("select stock from StockHolding stock where stock.ownerId = ?1")
    List<StockHolding> findByOwnerId(int ownerId);

}
