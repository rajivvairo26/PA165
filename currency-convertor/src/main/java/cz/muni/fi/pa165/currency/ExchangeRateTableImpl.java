
package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import javax.inject.Named;
import org.springframework.stereotype.Component;


/**
 *
 * @author xrajivv
 */
//@Component
@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {
    
   
    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
                  if (sourceCurrency == null) {
            throw new IllegalArgumentException("sourceCurrency is null");
                }
        if (targetCurrency == null) {
            throw new IllegalArgumentException("targetCurrency is null");
                    }
            
         if (sourceCurrency.getCurrencyCode().equals("CZK") && targetCurrency.getCurrencyCode().equals("IDR")){
           return new BigDecimal("675");
                }
         else return null;
    }
}
