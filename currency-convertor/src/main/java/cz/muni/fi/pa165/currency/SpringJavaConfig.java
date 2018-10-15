
package cz.muni.fi.pa165.currency;

import javax.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * @author xrajivv
 */
@Configuration
@ComponentScan("cz.muni.fi.pa165.currency")
@EnableAspectJAutoProxy
public class SpringJavaConfig {
    
     @Inject
    private ExchangeRateTable exchangeRateTable;
    // private ExchangeRateTable exchangeRateTable = new ExchangeRateTableImpl ();
      @Bean
    public CurrencyConvertor currencyConvertor1() {
        System.err.println("Creating Currency Convertor");
        return new CurrencyConvertorImpl(exchangeRateTable);
    }
    
}
