import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

public class TestGeoService {
    @Test
    void messageSenderImplRusText () {
        String ipString = "172.123.12.19";
        Location location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp(ipString)).thenReturn(location);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipString);

        assertThat(messageSender.send(headers), is(equalTo("Добро пожаловать")));
    }

    @Test
    void messageSenderImplEngText () {
        String ipString = "96.44.183.149";
        Location location = new Location("New York", Country.USA, " 10th Avenue", 32);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(geoService.byIp(ipString)).thenReturn(location);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipString);

        assertThat(messageSender.send(headers), is(equalTo("Welcome")));
    }

    @Test
    void geoServiceImplMoscowIp () {
        String ipString = "172.0.32.11";
        Location result = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
        GeoService geoService = new GeoServiceImpl();

        assertThat(geoService.byIp(ipString), is(equalTo(result)));
    }

    @Test
    void geoServiceImplNewYorkIp () {
        String ipString = "96.44.183.149";
        Location result = new Location("New York", Country.USA, " 10th Avenue", 32);
        GeoService geoService = new GeoServiceImpl();

        assertThat(geoService.byIp(ipString), is(equalTo(result)));
    }

    @Test
    void LocalizationServiceRussia () {
        Country country = Country.RUSSIA;
        String result ="Добро пожаловать";

        LocalizationService localizationService = new LocalizationServiceImpl();
        assertThat(localizationService.locale(country), is(equalTo(result)));
    }

    @Test
    void LocalizationServiceUSA () {
        Country country = Country.USA;
        String result ="Welcome";

        LocalizationService localizationService = new LocalizationServiceImpl();
        assertThat(localizationService.locale(country), is(equalTo(result)));
    }




}
