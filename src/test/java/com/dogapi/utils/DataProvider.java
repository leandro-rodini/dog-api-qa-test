package com.dogapi.utils;

import java.util.stream.Stream;

public final class DataProvider {

    private DataProvider() {
    }

    public static Stream<String> validBreeds() {
        return Stream.of("hound", "bulldog", "retriever");
    }

    public static Stream<String> invalidBreeds() {
        return Stream.of("teste1", "teste2");
    }
}
