package du.lessons.parking.model;

public enum CarBody {

    SEDAN("Седан"), UNIVERSAL("Универсал"), HATCHBACK("Хэчбэк"), PICKUP("Пикап"), BUS("Автобус");

    private String description;

    CarBody(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
