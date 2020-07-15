package com.petproject.store.services;

import com.petproject.store.model.Seller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SellersService {

    public static void inviteSellers(List<Seller> sellers, int sellersCount) {
        sellers.addAll(Stream.generate(() -> new Seller()).limit(sellersCount).collect(Collectors.toList()));
    }

}
