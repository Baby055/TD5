package hei.school.TD5.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class StockValue {
    private Double quantity;
    private Unit unit;

    // Constructeurs, getters, setters...
    public StockValue() {}

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
}
