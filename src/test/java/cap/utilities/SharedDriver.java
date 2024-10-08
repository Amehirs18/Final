package cap.utilities;

import cap.helpers.Constants;
import org.openqa.selenium.WebDriver;

/**
 * Created by codoid-pc on 29-08-2022.
 */
public class SharedDriver {

    protected static WebDriver driver;
    protected static WebDriver mobileDriver;
    protected static WebDriver windowsDriver;
    public static long randomID;
    public static String strExecutionID;

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver getWindowDriver() {
        return windowsDriver;
    }

    public static WebDriver getMobileDriver() {
        return mobileDriver;
    }

    public static String strUniqueValueForGYRA;


    static {
        strUniqueValueForGYRA = DateUtil.getCurrentDate("-MM-dd-yy-hh-mm-ss");

        strExecutionID = RandomGeneratorUtil.getRandomString();
        TestDataUtil.loadData(Constants.TESTDATA_PATH, System.getProperty(Constants.ENV_VARIABLE_APPLICATION));
        String strExecutionType = System.getProperty(Constants.ENV_VARIABLE_EXECUTION_TYPE, "");
        System.out.println("\n >> Execution String: " + strExecutionID);

        if (strExecutionType.equalsIgnoreCase("BROWSER")) {
            //Launch WebDriver
            driver = DriverUtil.getDriver();
            System.out.println("<----------------------WeDriver is launched---------------------->");
        } else if (strExecutionType.equalsIgnoreCase("MOBILE")) {
            // Mobile Driver
            mobileDriver = DriverUtil.getMobileDriver();
            System.out.println("<----------------------Mobile Driver is launched---------------------->");
        } else if (strExecutionType.equalsIgnoreCase("WEBMOBILE")) {
            //Launch WebDriver
            driver = DriverUtil.getDriver();
            System.out.println("<----------------------WebDriver is launched---------------------->");
            // Mobile Driver
            mobileDriver = DriverUtil.getMobileDriver();
            System.out.println("<----------------------Mobile Driver is launched---------------------->");
        } else if (strExecutionType.equalsIgnoreCase("API")) {
            System.out.println("<------------- API Suite started... ------------->");
        }
    }

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            String strExecutionType = System.getProperty(Constants.ENV_VARIABLE_EXECUTION_TYPE, "");
            if (strExecutionType.equalsIgnoreCase("BROWSER")) {
                driver.quit();
            } else if (strExecutionType.equalsIgnoreCase("MOBILE"))
                mobileDriver.quit();
            else if (strExecutionType.equalsIgnoreCase("WINDOWS"))
                windowsDriver.quit();
            else if (strExecutionType.equalsIgnoreCase("BOTH")) {
                driver.quit();
                windowsDriver.quit();
            } else if (strExecutionType.equalsIgnoreCase("WEBMOBILE")) {
                driver.quit();
                mobileDriver.quit();
            } else if (strExecutionType.equalsIgnoreCase("API")) {
                System.out.println("<------------- API Suite Completed... ------------->");
            }
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    /*public SharedDriver(){
        super(driver);
        //driver = getDriver();

    }*/

}
