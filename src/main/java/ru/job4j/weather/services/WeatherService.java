package ru.job4j.weather.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.model.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Sevastopol", 25));
        weathers.put(7, new Weather(7, "Yalta", 28));
        weathers.put(8, new Weather(8, "Poltava", 20));
    }

    public Mono<Weather> findById(int id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Mono<Weather> findHottestCity() {
        return Mono.justOrEmpty(weathers.values()
                .stream()
                .max(Comparator.comparing(Weather::getTemperature))
                .orElse(null));
    }

    public Flux<Weather> allCityWithTemperGreateThen(int id) {
        return Flux.fromIterable(weathers.values()
                .stream()
                .filter(weather -> weather.getTemperature() > id)
                .collect(Collectors.toList()));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }
}
