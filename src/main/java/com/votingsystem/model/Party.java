package com.votingsystem.model;

public class Party {
    private int id;
    private String name;
    private String symbol;
    private int voteCount;

    public Party() {}

    public Party(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.voteCount = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
