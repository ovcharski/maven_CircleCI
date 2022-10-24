package helperClasses;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static helperClasses.SeleniumUtil.getCurrentMethodName;
import static helperClasses.TestProperty.*;
import static helperClasses.UtilityMethods.*;
import static helperClasses.UtilityMethods.doesStringContainSomeText;


public class BaseTestClass {
    private static ChromeDriverService service;
    public static WebDriver driver;

    long classStartTime;
    long classEndTime;
    long classDurationInSeconds;
    long testStartTime;
    long testEndTime;
    long testDurationInSeconds;
    long methodStartTime;
    long methodEndTime;
    long methodDurationInSeconds;

    public static void createDriver() {
        logStringIntoConsole("**********************************************");
        logStringIntoConsole("***********************");
        logStringIntoConsole("**********************************************");
        logStringIntoConsole("***********************");
        logStringIntoConsole("|***| Browser: " + BROWSER);


        if (doesStringContainSomeText(BROWSER, "firefox")) {
            logStringIntoConsole("Creating the instance of FIREFOX DRIVER...");
            driver = new FirefoxDriver();

        } else if (doesStringContainSomeText(BROWSER, "chrome")) {
            logStringIntoConsole("Creating the instance of CHROME...");


            ChromeOptions options = new ChromeOptions();
            logStringIntoConsole("***CHECKING WHETHER TO RUN IN HEADLESS MODE");
            if (HEADLESS.equalsIgnoreCase("true")) {
                logStringIntoConsole(HEADLESS);
                options.addArguments("headless");
                options.addArguments("--disable-gpu");
                logStringIntoConsole("Argument 'headless' and 'disable-gpu' ADDED to ChromeOptions.");
            } else {
                logStringIntoConsole("Argument 'headless' SKIPPED.");
            }
            options.addArguments("test-type=browser");
            options.addArguments("--disable-web-security");
            options.addArguments("--no-proxy-server");
            options.addArguments("no-sandbox");

            //Window Size
            //options.addArguments("--window-size=904,768");
            options.addArguments("--start-maximized");

            options.addArguments("--ignore-certificate-errors");
            //options.addArguments("--incognito");
            options.addArguments("--enable-precise-memory-info");
            options.addArguments("--disable-geolocation");

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);


            driver = new RemoteWebDriver(service.getUrl(), options);
        } else {
            try {
                throw new Exception(TestProperty.BROWSER + " NOT supported.  Choose either Firefox or Chrome.");
            } catch (Exception e) {
                logError("The requested browser is NOT supported! Choose either Firefox or Chrome.");
            }
        }
    }

    public static void openURL(String url) {
        createDriver();

        logStringIntoConsole("URL: " + url);


        driver.get(url);
        driver.manage().timeouts().implicitlyWait(WAITING_TIME, TimeUnit.SECONDS);

        logStringIntoConsole("...completed OpenURL | " + getCurrentMethodName());
    }

    public static void closeBrowser() {
        driver.close();
    }

    public static void setDriverToConfigWaitingTime() {
        driver.manage().timeouts().implicitlyWait(WAITING_TIME, TimeUnit.SECONDS);
    }

    public static void setDriverToConfigWaitingTime(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void closeAllBrowsers() {
        logStringIntoConsole("......calling 'helper.SeleniumUtil.closeAllBrowsers' as test class completed.");

        try {
            if (!(driver == null)) {
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> handleIt = handles.iterator();

                while (handleIt.hasNext()) {
                    String handle = handleIt.next();
                    driver.switchTo().window(handle);
                    closeBrowser();
                }
            }

            //killChromeDriver();
        } catch (Exception e) {
        }
    }


    @BeforeGroups("smoke")
    public void _startSmokeTests() {
        logStringIntoConsole("Starting the SMOKE TEST run...");
    }

    @BeforeTest()
    public void startDriverService() {
        final File file = new File(System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.toString());

        try {
            service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(file.toString()))
                    .usingAnyFreePort()
                    .build();
            service.start();
        } catch (IOException e) {
            logError(e.toString());
        }

        testStartTime = System.nanoTime();
    }


    @BeforeMethod()
    public void _getTestDetails(ITestContext ctx, Method method) {
        logStringIntoConsole("****************************");
        logStringIntoConsole("******* TEST DETAILS *******");

        String testName = ctx.getCurrentXmlTest().getName();
        logStringIntoConsole("Test Name: " + testName);

        String methodName = method.getName();
        logStringIntoConsole("Method: " + methodName);

        logStringIntoConsole("****************************");
        logStringIntoConsole("****************************");


        classStartTime = System.nanoTime();
        methodStartTime = System.nanoTime();
    }


    @AfterMethod
    public void _afterMethod(ITestResult result, Method method, ITestContext ctx) {
        methodEndTime = System.nanoTime();
        long durationInNano = (methodEndTime - methodStartTime);
        methodDurationInSeconds = TimeUnit.NANOSECONDS.toSeconds(durationInNano);
        String durationInSecondsString = methodDurationInSeconds + "s";
        logStringIntoConsole("Method Duration: " + durationInSecondsString);

        //Get method name for mapping.
        String methodName = method.getName();



        logStringIntoConsole("### Test Method Complete: " + DateUtil.getFormattedCurrentDateTime());
    }

    @AfterClass
    public void _afterClass(ITestContext ctx) {
        classEndTime = System.nanoTime();
        long durationInNano = (classEndTime - classStartTime);
        classDurationInSeconds = TimeUnit.NANOSECONDS.toSeconds(durationInNano);
        String durationInSecondsString = classDurationInSeconds + "s";
        logStringIntoConsole("Class Duration: " + durationInSecondsString);

        //Get class path for mapping.
        String classPath = this.getClass().getName();


    }



    protected boolean hasClassFailedTests(ITestContext context) {
        Class clazz = this.getClass();
        return context.getFailedTests().getAllMethods().stream().anyMatch(it ->
                it.getRealClass().equals(clazz));
    }

    @AfterTest
    public void _testLogging(ITestContext ctx) {
        testEndTime = System.nanoTime();
        long durationInNano = (testEndTime - testStartTime);
        testDurationInSeconds = TimeUnit.NANOSECONDS.toSeconds(durationInNano);
        String durationInSecondsString = testDurationInSeconds + "s";
        logStringIntoConsole("Test Duration: " + durationInSecondsString);


        String testName = ctx.getCurrentXmlTest().getName();


        closeAllBrowsers();
        service.stop();
    }


    @AfterGroups("smoke")
    public void _teardownSmokeTests() {
      logStringIntoConsole("Completed the SMOKE TEST run...");
    }
}

