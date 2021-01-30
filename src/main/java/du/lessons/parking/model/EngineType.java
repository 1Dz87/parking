package du.lessons.parking.model;

public enum EngineType {

    FUEL("Бензин"),
    DIESEL("Дизель"),
    ELECTRIC("Электро"),
    HYBRID("Гибрид");

    private String description;

    EngineType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
