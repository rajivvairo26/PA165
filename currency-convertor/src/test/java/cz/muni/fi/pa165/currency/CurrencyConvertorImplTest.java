package cz.muni.fi.pa165.currency;

import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Currency;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {
    private static Currency CZK = Currency.getInstance("CZK");
    private static Currency IDR = Currency.getInstance("IDR");

    @Mock
    private ExchangeRateTable exchangeRateTable;

    private CurrencyConvertor currencyConvertor;
    
    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(exchangeRateTable.getExchangeRate(CZK, IDR))
                .thenReturn(new BigDecimal("0.1"));
        
        
        assertEquals(new BigDecimal("1.00"), currencyConvertor.convert(CZK, IDR, new BigDecimal("10.050")));
        assertEquals(new BigDecimal("1.01"), currencyConvertor.convert(CZK, IDR, new BigDecimal("10.051")));
        assertEquals(new BigDecimal("1.01"), currencyConvertor.convert(CZK, IDR, new BigDecimal("10.149")));
        assertEquals(new BigDecimal("1.02"), currencyConvertor.convert(CZK, IDR, new BigDecimal("10.150")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
       // fail("Test is not implemented yet.");
       expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(null, IDR, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
       // fail("Test is not implemented yet.");
       expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(CZK, null, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
       // fail("Test is not implemented yet.");
       expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(CZK, IDR, null);
    }

    @Test
    public void testConvertWithUnknownCurrency()throws ExternalServiceFailureException {
       // fail("Test is not implemented yet.");
       when(exchangeRateTable.getExchangeRate(CZK, IDR))
                .thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(CZK, IDR, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(CZK, IDR))
                .thenThrow(UnknownExchangeRateException.class);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(CZK, IDR, BigDecimal.ONE);
         //fail("Test is not implemented yet.");
    }

}
