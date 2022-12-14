import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestTextMessageSender {
    @ParameterizedTest
    @MethodSource("sourceByMessageSender")
    public void checkMessageSender(String ip, Location location, Country country, String text) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(text);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String send = messageSender.send(headers);

        Assertions.assertEquals(text, send);
    }

    private static Stream<Arguments> sourceByMessageSender() {
        return Stream.of(
                arguments("172.0.32.11", new Location("Moscow", Country.RUSSIA, null, 0), Country.RUSSIA, "Добро пожаловать"),
                arguments("96.44.183.149", new Location("New York", Country.USA, null, 0), Country.USA, "Welcome")
        );
    }
}
