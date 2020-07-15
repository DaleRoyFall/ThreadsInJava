package com.petproject.store.model;

import com.petproject.store.services.StorePerformanceService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Stall {

    Logger log;
    List<Seller> sellers;
    ExecutorService service;
    List<Future<?>> futures;
    AtomicInteger servedBuyers = new AtomicInteger(0);
    StorePerformanceService performanceService = new StorePerformanceService();

    public Stall(Logger log, List<Seller> sellers) {
        this.log = log;
        this.sellers = sellers;

        futures = new ArrayList<>();
    }

    public void setService(int size) {
        this.service = Executors.newFixedThreadPool(size);;
    }

    public synchronized void trade(Queue<Buyer> buyers) {
        servedBuyers.set(0);
        performanceService.startServeBuyers();

        // Determine minimum number of person to trade
        int personSize = Math.min(sellers.size(), buyers.size());

        // Sellers do work here
        for (int i = 0; i < personSize; i++) {
            Seller seller = sellers.get(i);
            Buyer buyer = buyers.poll();

            // Get status of seller work
            if(buyer != null) {
                Future<?> sellerWorkStatus = service.submit(() -> {
                    seller.serveTheBuyer(buyer);
                    servedBuyers.incrementAndGet();
                });

                // Add status in list
                futures.add(sellerWorkStatus);
            }
        }

        if(checkStatus()) {
            log.info(performanceService.checkPerformance(servedBuyers.get()));
        } else {
            log.info("Waiting for response");
        }
    }

    private boolean checkStatus() {
        if (futures.isEmpty())
            return true;

        // Wait for future response
        for(Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Verify with AND operation
        boolean finalStatus = true;
        for(Future<?> future : futures){
            finalStatus &= future.isDone();
        }

        return finalStatus;
    }
}
