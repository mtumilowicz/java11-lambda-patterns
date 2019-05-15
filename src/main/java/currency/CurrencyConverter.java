package currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mtumilowicz on 2019-05-15.
 * <p>
 * TO-DO (working in progress)
 */
public interface CurrencyConverter {

    BigDecimal convert(BigDecimal amount);

    interface To {
        BigDecimal convert(BigDecimal amount, String toCurrency);
        
        default CurrencyConverter to(String toCurrency) {
            return amount -> convert(amount, toCurrency);
        }
    }
    
    interface From {
        BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency);
        
        default To from(String fromCurrency) {
            return (amount, toCurrency) -> convert(amount, fromCurrency, toCurrency);
        }
    }

    static From of(LocalDate date) {

        return (amount, fromCurrency, toCurrency) -> {

            Path path = Paths.get("src/main/resources/currency.txt");
            try (Stream<String> lines = Files.lines(path)) {

                Map<String, BigDecimal> converterMap =
                        lines.skip(1L)
                                .collect(
                                        Collectors.toMap(
                                                line -> line.substring(0, 3),
                                                line -> new BigDecimal(line.substring(4))
                                        )
                                );

                return amount.multiply(converterMap.get(toCurrency).divide(converterMap.get(fromCurrency),
                        RoundingMode.DOWN));

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        };
    }

    static void main(String[] args) {
        System.out.println(CurrencyConverter.of(LocalDate.of(2018, 11, 5))
                .from("EUR")
                .to("GBP")
                .convert(BigDecimal.valueOf(1000)));
    }
}
