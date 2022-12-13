import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class TestLocalizationService {
    @ParameterizedTest
    @EnumSource(Country.class)
    public void checkLocalizationService(Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        String text = localizationService.locale(country);
        if (country == Country.RUSSIA) {
            Assertions.assertEquals(text, "Добро пожаловать");
        } else
            Assertions.assertEquals(text, "Welcome");
    }
}
