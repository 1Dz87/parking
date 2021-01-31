package du.lessons.parking.lib.exceptions;

public class CarNotFoundException extends Exception {

    public CarNotFoundException() {
        super("Искомый автомобиль не найден");
    }
}
