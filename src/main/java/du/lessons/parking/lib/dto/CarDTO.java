package du.lessons.parking.lib.dto;

import du.lessons.parking.model.CarBody;
import du.lessons.parking.model.EngineType;

public class CarDTO {

    private String model;

    private CarBody body;

    private Float engineValue;

    private EngineType type;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarBody getBody() {
        return body;
    }

    public void setBody(CarBody body) {
        this.body = body;
    }

    public Float getEngineValue() {
        return engineValue;
    }

    public void setEngineValue(Float engineValue) {
        this.engineValue = engineValue;
    }

    public EngineType getType() {
        return type;
    }

    public void setType(EngineType type) {
        this.type = type;
    }
}
