import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestGeoService {
    @ParameterizedTest
    @MethodSource("sourceByGeoLocation")
    public void checkReturnGeoLocation(String strings, Location exceptedLocation) {
        GeoServiceImpl geoService = new GeoServiceImpl();

        Location actualLocation = geoService.byIp(strings);

        Assertions.assertEquals(exceptedLocation, actualLocation);
    }

    private static Stream<Arguments> sourceByGeoLocation() {
        return Stream.of(
                arguments("127.0.0.1", new Location(null, null, null, 0)),
                arguments("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                arguments("172.", new Location("Moscow", Country.RUSSIA, null, 0)),
                arguments("96.", new Location("New York", Country.USA, null, 0)),
                arguments("", null)
        );
    }

    @Test
    public void checkThrowExceptionGeoLocationByCoordinates() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(new Random().nextDouble(), new Random().nextDouble()));
    }
}
