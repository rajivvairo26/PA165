package cz.muni.fi.pa165.currency;


import java.math.BigDecimal;
import java.util.Currency;
import java.math.RoundingMode;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;




/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
//@Named
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final static Logger logg = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    @Inject
    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logg.trace("convert({},{},{})",sourceCurrency, targetCurrency, sourceAmount);
        if (sourceCurrency == null) {
            throw new IllegalArgumentException("sourceCurrency is null");
        }
        if (targetCurrency == null) {
            throw new IllegalArgumentException("targetCurrency is null");
        }
        if (sourceAmount == null) {
            throw new IllegalArgumentException("sourceAmount is null");
        }
        try {
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (exchangeRate == null) {
                throw new UnknownExchangeRateException("ExchangeRate is unknown");
            }
            return exchangeRate.multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
        } catch (ExternalServiceFailureException ex) {
            throw new UnknownExchangeRateException("Error when fetching exchange rate", ex);
        }
      // return null;

    }

    /*@Override
    public void afterPropertiesSet() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

}
