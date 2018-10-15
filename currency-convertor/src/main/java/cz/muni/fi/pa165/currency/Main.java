
package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author xrajivv
 */
public class Main {
    
    public static void main(String ... args) {

       
       springXmlContext(); //Main with Applicatiion Context XMl file 
       springAnnotationContext();//Main Annotations, comment @named CurrencyConverterImpl
        springJavaConfigContext(); //Main Java config 
       noSpring(); //Main no spring
    }
    private static void springXmlContext() {

        ApplicationContext applicationContext 
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        CurrencyConvertor currencyConvertor 
                = applicationContext.getBean(CurrencyConvertor.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("CZK"), Currency.getInstance("IDR"), BigDecimal.ONE).toString());

    }
    
        private static void springAnnotationContext() {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");

        CurrencyConvertor currencyConvertor 
                = applicationContext.getBean(CurrencyConvertor.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("CZK"), Currency.getInstance("IDR"), BigDecimal.ONE).toString());

    }
       

    private static void springJavaConfigContext() {

        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(SpringJavaConfig.class);

         CurrencyConvertor currencyConvertor 
                = applicationContext.getBean("currencyConvertor1", CurrencyConvertor.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("CZK"), Currency.getInstance("IDR"), BigDecimal.ONE).toString());

    }
    
     private static void noSpring() {
        

        ExchangeRateTable exchangeRateTable = new ExchangeRateTableImpl ();
        CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
        System.out.println(currencyConvertor.convert(Currency.getInstance("CZK"), Currency.getInstance("IDR"), BigDecimal.ONE).toString());
        
    }

    
}
