package practice9_log4j2;

/**
 * Created by jet on 2017/6/12.
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyApplication {
    static Logger logger = LogManager.getLogger(MyApplication.class.getName());

    public boolean doIt() {
        logger.entry();   //Log entry to a method
        logger.error("Did it again!");   //Log a message object with the ERROR level
        logger.exit();    //Log exit from a method
        return false;
    }
}