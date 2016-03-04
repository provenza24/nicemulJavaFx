package nicemul.business.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextHolder implements ApplicationContextAware {

    private static ClassPathXmlApplicationContext xmlAppContext;
    
    private static ApplicationContext context = null;

     public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        context = ctx;        
    }

     public static ApplicationContext getContext() {
        return context;
    }

    public static ClassPathXmlApplicationContext getXmlAppContext() {
        return xmlAppContext;
    }

    public static void setXmlAppContext(ClassPathXmlApplicationContext applicationContext) {
        ApplicationContextHolder.xmlAppContext = applicationContext;
    }

}