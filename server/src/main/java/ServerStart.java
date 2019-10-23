import repository.utils.CommonUtils;

public class ServerStart {
    public static void main(String[] args) {
        Server s = CommonUtils.getFactory().getBean(Server.class);
        System.out.println("Starting server . . .");
        s.startServer();
//          ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:server-config.xml");
    }
}
