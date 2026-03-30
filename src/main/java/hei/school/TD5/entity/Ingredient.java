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

    // Getters, setters, constructeurs...

    // Méthode métier (existante) pour calculer le stock à une date
    public StockValue getStockValueAt(Instant t) {
        if (stockMovements == null) return null;
        // ... même code que dans l'ancienne classe Ingredient
    }
}