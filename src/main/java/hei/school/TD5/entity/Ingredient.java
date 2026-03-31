package hei.school.TD5.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    private Double price;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockMovement> stockMovements;

    public Ingredient() {}

    public Ingredient(Integer id, CategoryEnum category, String name, Double price, List<StockMovement> stockMovements) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stockMovements = stockMovements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }

    public StockValue getStockValueAt(Instant t) {
        if (stockMovements == null) return null;

        Map<Unit, List<StockMovement>> unitSet = stockMovements.stream()
                .collect(Collectors.groupingBy(sm -> sm.getValue().getUnit()));

        if (unitSet.keySet().size() > 1) {
            throw new RuntimeException("Multiple units found");
        }

        List<StockMovement> filtered = stockMovements.stream()
                .filter(sm -> !sm.getCreationDatetime().isAfter(t))
                .toList();

        double movementIn = filtered.stream()
                .filter(sm -> sm.getType() == MovementTypeEnum.IN)
                .flatMapToDouble(sm -> DoubleStream.of(sm.getValue().getQuantity()))
                .sum();

        double movementOut = filtered.stream()
                .filter(sm -> sm.getType() == MovementTypeEnum.OUT)
                .flatMapToDouble(sm -> DoubleStream.of(sm.getValue().getQuantity()))
                .sum();

        StockValue stockValue = new StockValue();
        stockValue.setQuantity(movementIn - movementOut);
        stockValue.setUnit(unitSet.keySet().stream().findFirst().get());

        return stockValue;
    }
}