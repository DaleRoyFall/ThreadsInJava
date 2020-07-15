package com.petproject.store.services;

public class StorePerformanceService {

    private static long allServedBuyers = 0;
    private long tradingStartTime = 0;
    private long serveTime = 0;

    public void startServeBuyers() {
        tradingStartTime = System.currentTimeMillis();
    }

    public String checkPerformance(int servedBuyers) {
        allServedBuyers += servedBuyers;
        serveTime = (System.currentTimeMillis() - tradingStartTime);
        return String.format("All served buyers: %d, Serve %d more buyers for: %d milliseconds. Store performance: %d",
                allServedBuyers, servedBuyers, serveTime, calculateStorePerformance(servedBuyers, serveTime));
    }

    private int calculateStorePerformance(int servedBuyers, long serveTime) {
        return (int) (serveTime / 1000 * servedBuyers);
    }
}
