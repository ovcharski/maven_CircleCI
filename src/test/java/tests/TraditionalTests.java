package tests;

import helperClasses.DriverFactory;
import helperClasses.BaseTestClass;
import helperClasses.SeleniumUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;


import static helperClasses.SeleniumUtil.*;
import static helperClasses.TestProperty.URL;

public class TraditionalTests extends BaseTestClass {
    @BeforeClass
    public void setUp() throws IOException {
        openURL(URL);
    }

    @Test
    public void validateLoaded() {
        verifyPageTitle("Queries of Life");
    }

}
