package du.lessons.parking;

import du.lessons.parking.lib.util.Utils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void stringIsEmptyTest() {
        String nullStr = null;
        String emptyStr = "";
        String str = "some string";

        assertAll(
                () -> assertTrue(Utils.stringIsEmpty(nullStr)),
                () -> assertTrue(Utils.stringIsEmpty(emptyStr)),
                () -> assertFalse(Utils.stringIsEmpty(str))
        );
    }

    private static Stream<Arguments> checkboxToBooleanTest() {
        return Stream.of(
                Arguments.of("on", true),
                Arguments.of("off", false),
                Arguments.of("", false),
                Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void checkboxToBooleanTest(String value, Boolean resultValue) {
        assertEquals(resultValue, Utils.checkboxToBoolean(value));
    }

    public static Stream<Arguments> convertTest() {
        return Stream.of(
                Arguments.of("2021-02-20", new Date())
        );
    }

    @ParameterizedTest
    @MethodSource
    public void convertTest(String value, Date date) {
        Date resultDate = DateUtils.truncate(Utils.convert(value), Calendar.DAY_OF_MONTH);
        Date expectedDate = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        assertEquals(expectedDate, resultDate);
    }

    @Test
    public void convertTestFails() {
        assertThrows(RuntimeException.class, () -> Utils.convert("21.02.2021"));
    }

}
