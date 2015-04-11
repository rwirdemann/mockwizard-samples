package orderservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    @JsonProperty
    private String _id;

    @JsonProperty
    private String symbol;

    @JsonProperty
    private double price;

    @JsonProperty
    private int count;

    @JsonProperty
    private double limit;

    public Order() {
    }

    public Order(String symbol, int count) {
        this.symbol = symbol;
        this.count = count;
    }
    
    public Order withLimit(double limit) {
        this.limit = limit;
        return this;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getLimit() {
        return limit;
    }
}

