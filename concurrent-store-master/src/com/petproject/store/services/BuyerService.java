package com.petproject.store.services;

import com.petproject.store.model.Buyer;

import java.util.Queue;

public class BuyerService {

    public static void inviteNewBuyers(Queue<Buyer> buyers, int storeSize) {
        if (buyers.size() < storeSize) {
            for(int i = 0; i < (storeSize - buyers.size()); i++) {
                buyers.add(new Buyer());
            }
        }
    }

}
