package com.example.graphQL.Repositories;

import com.example.graphQL.Models.Stock;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockRepository implements DataFetcher<List<Stock>> {

    @Override
    public List<Stock> get(DataFetchingEnvironment environment) {
        List<Stock> stocks = new ArrayList<>();

        Stock stock = new Stock();

        stock.setStockValue("10");
        stock.setEPS("1");
        stock.setDividend("99");
        stock.setPE_Ratio("0.79");
        stock.setMarketCap("0.66");
        stock.setPreviousClose("0.55");
        stock.setPreviousOpen("88");
        stock.setVolume("2.2");
        stock.setAverageVolume("111");
        stock.setTicker("123");

        stocks.add(stock);

        return stocks;
    }
}
