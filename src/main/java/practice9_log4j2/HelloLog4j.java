package practice9_log4j2;

/**
 * Created by jet on 2017/6/12.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HelloLog4j {
    private static Logger logger = LogManager.getLogger("HelloLog4j");
    public static void main(String[] args) {
        MyApplication myApplication =  new MyApplication();

        logger.entry();
        logger.info("Hello, World!");
        myApplication.doIt();
        logger.error("Hello, World!");
        logger.exit();
    }
}
