package com.petproject.store.model;

public class Seller {

    public synchronized void serveTheBuyer(Buyer buyer) {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            buyer.isServed = true;
        }
    }

}
