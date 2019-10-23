package repository.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonUtils {
    private static ApplicationContext factory;
    private CommonUtils(){}

    public static ApplicationContext getFactory(){
        if (factory == null){
            factory = new ClassPathXmlApplicationContext("classpath*:persistance-config.xml", "classpath*:server-config.xml","classpath*:client-config.xml");
        }
        System.out.println(factory.toString());
        return factory;
    }
}
