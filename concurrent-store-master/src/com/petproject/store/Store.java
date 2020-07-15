package com.petproject.store;

import com.petproject.store.model.Buyer;
import com.petproject.store.model.Seller;
import com.petproject.store.model.Stall;
import com.petproject.store.services.BuyerService;
import com.petproject.store.services.LogService;
import com.petproject.store.services.SellersService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

public class Store {

    final Logger log = new LogService().log;

    Queue<Buyer> buyers = new LinkedList<>();
    List<Seller> sellers = new ArrayList<>();
    Stall stall = new Stall(log, sellers);
    /**
     * Maximum buyer count
     */
    int storeSize = 1000;

    public void openStore() {
        SellersService.inviteSellers(sellers, 100);
        BuyerService.inviteNewBuyers(buyers, storeSize);
        log.info("Store is open");
        startTrading();
    }

    private void startTrading() {
        log.info("Start trading");
        while(true) {
            stall.trade(buyers);
            BuyerService.inviteNewBuyers(buyers, storeSize);
        }
    }

}
