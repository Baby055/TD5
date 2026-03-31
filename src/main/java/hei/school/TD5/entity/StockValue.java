package hei.school.TD5.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class StockValue {
    private Double quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    // constructeurs, getters, setters...
    public StockValue() {}

    public StockValue(Double quantity, Unit unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
}