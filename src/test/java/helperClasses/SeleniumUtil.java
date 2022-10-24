package helperClasses;

import org.kohsuke.rngom.parse.host.Base;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import static helperClasses.BaseTestClass.setDriverToConfigWaitingTime;
import static helperClasses.TestProperty.WAITING_TIME;
import static helperClasses.UtilityMethods.*;


public class SeleniumUtil extends BaseTestClass {
    public static String s;




    //Methods
    public static void switchToDynamicFrame() {
        try {
            setDriverToConfigWaitingTime();
            microSleep();
            By dynamicFrame = By.cssSelector("div[style='display: block;'] iframe");
            switchToFrame(dynamicFrame);
        } catch (TimeoutException e) {
            logStringIntoConsole("Timed Out | During " + getCurrentMethodName());
        } catch (NoSuchElementException e) {
            logStringIntoConsole("No Frame Found | During " + getCurrentMethodName());
        }

        setDriverToConfigWaitingTime();
    }

    public static void highlightElement(By element) {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element(element), "color: yellow; border: 4px solid yellow;");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element(element), "");
        }
    }

    private static void checkPageIsReady() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (js.executeScript("return document.readyState").toString().equals("complete")) {
            return;
        }

        for (int i = 0 ; i < 25 ; i++) {
            try {
                microSleep();
            } catch (Exception e) {
                logStringIntoConsole("Page Not Ready Yet!!!");
            }
            if (js.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
        }
    }

    public static boolean doesElementExist(By elementLocator) {
        boolean result = false;

        setDriverToConfigWaitingTime();

        try {
            logStringIntoConsole("...searching for �Element: " + elementLocator);
            WebElement myDynamicElement = (new WebDriverWait(driver, 1))
                    .until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            logStringIntoConsole("FOUND element.	| " + elementLocator);
            result = true;
        } catch (Exception Ex) {
            logStringIntoConsole("UNABLE to locate Element. | " + elementLocator);
            result = false;

        }


        setDriverToConfigWaitingTime();
        return result;
    }

    public static boolean verifyPageTitle(String expectedPageTitle) {
        doesStringContainSomeText(driver.getTitle(), expectedPageTitle);
        return driver.getTitle().contains(expectedPageTitle);
    }

    public static boolean doesElementExist(By elementIdentifier, int waitingTimeSec) {
        boolean result;
        setDriverToConfigWaitingTime(waitingTimeSec);


        try {
            logStringIntoConsole("...searching for Element: " + elementIdentifier);
            WebElement myDynamicElement = (new WebDriverWait(driver, waitingTimeSec))
                    .until(ExpectedConditions.visibilityOfElementLocated(elementIdentifier));
            logStringIntoConsole("FOUND element.	| " + elementIdentifier);
            result = true;
        } catch (org.openqa.selenium.TimeoutException Ex) {
            logStringIntoConsole("UNABLE to locate Element. | " + elementIdentifier);
            result = false;

        }


        setDriverToConfigWaitingTime();
        return result;
    }

    public static boolean doesElementExistNoLogging(By elementIdentifier) {
        boolean result;


        try {
            setDriverToConfigWaitingTime();
            WebElement myDynamicElement = (new WebDriverWait(driver, 1))
                    .until(ExpectedConditions.visibilityOfElementLocated(elementIdentifier));
            result = true;
        } catch (org.openqa.selenium.TimeoutException Ex) {
            result = false;
        }


        setDriverToConfigWaitingTime();
        return result;
    }

    public static boolean doesElementExist(WebElement elementLocator, int waitingTimeSec) {
        boolean result;

        setDriverToConfigWaitingTime(waitingTimeSec);

        switchToDynamicFrame();

        try {
            logStringIntoConsole("...searching for �lement: " + elementLocator);
            WebElement myDynamicElement = (new WebDriverWait(driver, waitingTimeSec))
                    .until(ExpectedConditions.visibilityOf(elementLocator));
            logStringIntoConsole("FOUND element.	| " + elementLocator);
            result = true;
        } catch (org.openqa.selenium.TimeoutException Ex) {
            logStringIntoConsole("UNABLE to locate Element. | " + elementLocator);
            result = false;

        }


        setDriverToConfigWaitingTime();
        return result;
    }

    public static boolean doesElementExist(WebElement elementLocator) {
        boolean result;

        setDriverToConfigWaitingTime();

        switchToDynamicFrame();


        try {
            logStringIntoConsole("...searching for �lement: " + elementLocator);
            WebElement myDynamicElement = (new WebDriverWait(driver, 1))
                    .until(ExpectedConditions.visibilityOf(elementLocator));
            logStringIntoConsole("FOUND element.	| " + elementLocator);
            result = true;
        } catch (org.openqa.selenium.TimeoutException Ex) {
            logStringIntoConsole("UNABLE to locate Element. | " + elementLocator);
            result = false;

        }


        setDriverToConfigWaitingTime();
        return result;
    }

    public static boolean doesFrameExist(By frameLocator, int waitingTimeSec) {
        setDriverToConfigWaitingTime(waitingTimeSec);


        try {
            logStringIntoConsole("...searching for Frame: " + frameLocator);
            switchToDefaultContent();
            WebElement myDynamicElement = (new WebDriverWait(driver, waitingTimeSec))
                    .until(ExpectedConditions.visibilityOfElementLocated(frameLocator));
            switchToFrame(frameLocator);
            logStringIntoConsole("FOUND frame. | " + frameLocator);
        } catch (org.openqa.selenium.TimeoutException Ex) {
            logError("Unable to locate Frame. | " + frameLocator);
            logError("Unable to locate Frame. | " + frameLocator);
            logError("Unable to locate Element: " + frameLocator);
            return false;

        }

        setDriverToConfigWaitingTime();
        return true;
    }


    /**
     * This method is used for element is present on page or not
     *
     * @param fieldLocator
     * @return true if element is present, other wise it return Fail
     */
    public static boolean isElementPresent(By fieldLocator) {

        try {
            logStringIntoConsole("Element: " + fieldLocator);
            element(fieldLocator);
            logStringIntoConsole("Element Present: " + fieldLocator);

        } catch (org.openqa.selenium.NoSuchElementException Ex) {
            logStringIntoConsole("Unable to locate Element: " + fieldLocator);
            return false;
        }
        return true;
    }

    public static void clearTextField(By elementIdentifier) {
        try {

            hoverAndClick(elementIdentifier);

            new WebDriverWait(driver, 1).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));

            element(elementIdentifier).clear();

        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
        } catch (InvalidElementStateException ex) {
            longWaitUntilLoaded();
        }
    }

    public static void sendKeysActionsToElement(By element, String desiredInput) {


        WebElement webElement = element(element);
        Actions builder = new Actions(driver);
        builder.click(webElement).sendKeys(desiredInput);
        waitUntilLoaded();
    }

    public static boolean sendKeysToElement(By elementIdentifier, String inputText) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 1) {
            try {
                clearTextField(elementIdentifier);

                new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                        visibilityOfElementLocated(elementIdentifier));


                element(elementIdentifier).sendKeys(inputText);
                waitUntilLoaded();
            } catch (StaleElementReferenceException e) {
                longWaitUntilLoaded();
                switchToDynamicFrame();

                new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                        visibilityOfElementLocated(elementIdentifier));


                element(elementIdentifier).sendKeys(inputText);
                waitUntilLoaded();
            } catch (WebDriverException e) {
                switchToDynamicFrame();

                new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                        visibilityOfElementLocated(elementIdentifier));

                element(elementIdentifier).sendKeys(inputText);
                waitUntilLoaded();
            }
            attempts++;
        }

        logStringIntoConsole("SendKeys: " + inputText + " | " + elementIdentifier);
        return result;
    }

    public static boolean sendKeysToElement(By elementIdentifier) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 1) {
            try {
                clearTextField(elementIdentifier);

                new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                        visibilityOfElementLocated(elementIdentifier));

                String randomString = "Automated " + UtilityMethods.returnRandomString(5);
                sendKeysToElement(elementIdentifier, randomString);
                waitUntilLoaded();

                logStringIntoConsole("*RANDOM* SendKeys: " + randomString + " | " + elementIdentifier);
            } catch (StaleElementReferenceException e) {
                longWaitUntilLoaded();
            }
            attempts++;
        }
        return result;
    }

    public static void ifPresentSendKeysToElement(By elementIdentifier, String textToInput) {
        setDriverToConfigWaitingTime();
        switchToDynamicFrame();
        if (doesElementExist(elementIdentifier)) {
            try {
                sendKeysToElement(elementIdentifier, textToInput);
            } catch (ElementNotVisibleException e) {
            }
        } else {
            logStringIntoConsole("Text Field NOT present. Continuing...");
        }


        setDriverToConfigWaitingTime();
    }

    public static void sendKeysToWebElementDelayPresent(By elementIdentifier, String inputText, int secondsDelay) {


        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));

        element(elementIdentifier).clear();
        sleep(secondsDelay);
        element(elementIdentifier).sendKeys(inputText);
        waitUntilLoaded();
        logStringIntoConsole("INPUT text: " + inputText + " | " + elementIdentifier);


    }

    public static void sendKeysToWebElementDelayPresentEnterPress(By elementIdentifier, String inputText, int secondsDelay) {
        element(elementIdentifier).clear();
        sleep(secondsDelay);
        element(elementIdentifier).sendKeys(inputText);
        element(elementIdentifier).sendKeys(Keys.ENTER);
        logStringIntoConsole("Input text: " + inputText + " | " + elementIdentifier);

    }

    public static void sendkeys_JS(By locator, String text, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + text + "'", element(locator));
        logStringIntoConsole("The text to send is " + text);

    }

    public static boolean isElementDisplayed(By elementLocator) {
        try {
            logStringIntoConsole("Element: " + elementLocator);
            element(elementLocator).isDisplayed();

        } catch (org.openqa.selenium.NoSuchElementException Ex) {
            logStringIntoConsole("Unable to locate Element: " + elementLocator);
            return false;
        }
        return true;
    }

    public static boolean isElementDisplayedString(String elementLocator, WebDriver driver) {
        try {
            logStringIntoConsole("Element: " + elementLocator);
            element(By.xpath(elementLocator)).isDisplayed();

        } catch (org.openqa.selenium.NoSuchElementException Ex) {
            logStringIntoConsole("Unable to locate Element: " + elementLocator);
            logStringIntoConsole("Unable to locate Element: " + elementLocator);
            return false;
        }
        return true;
    }

    public static WebElement element(By elementIdentifier) {
        try {
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));
        } catch (TimeoutException e) {

        } catch (NoSuchElementException e) {
            switchToDynamicFrame();
            try {
                new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                        visibilityOfElementLocated(elementIdentifier));
            } catch (Exception ex) {

            }
        }

        return driver.findElement(elementIdentifier);
    }

    public static List<WebElement> elements(By elementIdentifier) {
        try {
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));
        } catch (TimeoutException e) {
            logError(e.toString());
        } catch (NoSuchElementException e) {
            switchToDynamicFrame();
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));
        }

        return driver.findElements(elementIdentifier);
    }

    public static void hoverOverElement(By elementIdentifier) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        Actions builder = new Actions(driver);
        builder.moveToElement(element(elementIdentifier)).perform();
        logStringIntoConsole("HOVERED over element. | " + elementIdentifier + " | Text: " + element(elementIdentifier).getText());
    }

    private static boolean retryFindClick(By elementIdentifier) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                hoverAndClick(elementIdentifier);
            } catch (StaleElementReferenceException e) {
                longWaitUntilLoaded();
                switchToDynamicFrame();

                Actions builder = new Actions(driver);
                builder.moveToElement(element(elementIdentifier)).build().perform();
                element(elementIdentifier).click();
                waitUntilLoaded();
            }
            attempts++;
        }

        logStringIntoConsole("Retrying Click. | " + elementIdentifier);
        return result;
    }

    public static void hoverAndClickIgnoreExceptions(By elementIdentifier, int waitingTimeSec) {


        logStringIntoConsole("Attempting to Click on Element even if not present on page | " + elementIdentifier);
        try {
            hoverAndClick(elementIdentifier);
        } catch (TimeoutException e) {
        }


        ifPresentAcceptAlert();

    }

    public static void hoverAndClickIgnoreExceptions(By elementIdentifier) {
        logStringIntoConsole("Attempting to Click on Element even if not present on page | " + elementIdentifier);
        try {
            hoverAndClick(elementIdentifier);
        } catch (TimeoutException e) {
        }


        ifPresentAcceptAlert();

    }

    public static void clickByJSIgnoreExceptions(By elementIdentifier) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));

        try {
            logStringIntoConsole("Attempting to Click on Element even if not present on page | " + elementIdentifier);
            clickByJS(elementIdentifier);

        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
        }


    }

    public static void selectCheckbox(By elementIdentifier) {


        if (!element(elementIdentifier).isSelected()) {
            logStringIntoConsole("Selecting Checkbox Item | " + elementIdentifier);
            hoverAndClick(elementIdentifier);
        } else {
            logStringIntoConsole("Checkbox ALREADY selected | " + elementIdentifier);
        }
    }

    public static void unselectCheckbox(By elementIdentifier) {


        if (element(elementIdentifier).isSelected()) {
            logStringIntoConsole("Unselect Checkbox Item" + elementIdentifier);
            hoverAndClick(elementIdentifier);
        } else {
            logStringIntoConsole("Checkbox not checked, proceeding... | " + elementIdentifier);
        }
    }

    public static void selectCheckbox(WebElement elementIdentifier) {


        if (!elementIdentifier.isSelected()) {
            logStringIntoConsole("Selecting Checkbox: " + elementIdentifier.getText());
            elementIdentifier.click();
        } else {
            logStringIntoConsole("* Checkbox ALREADY selected * | " + elementIdentifier.getText());
        }
    }

    public static void hoverAndClick(By elementIdentifier) {
        logStringIntoConsole("Hover And Click | Element: " + elementIdentifier);


        Actions builder = new Actions(driver);
        try {
            builder.moveToElement(element(elementIdentifier)).click().build().perform();
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();

            hoverAndClickIgnoreExceptions(elementIdentifier);
        }
        logStringIntoConsole("Clicked. | " + elementIdentifier);

        ifPresentAcceptAlert();
    }

    public static void hoverAndClickViaElementText(String elementLabel) {
        hoverAndClick(getElementFromText(elementLabel));
    }

    /**
     * Click on a random element within multiple acceptable options.  This is helpful for checkboxes or radio buttons
     * that have alternating but not branching logic implemented.
     *
     * @param identifiedElements
     */
    public static void hoverAndClickRandomFromMultipleElements(By identifiedElements) {

        try {
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(identifiedElements));
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    elementToBeClickable(identifiedElements));
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();
        }

        int matchingElements = elements(identifiedElements).size();
        logStringIntoConsole("Matching # of Elements: " + matchingElements);


        if (Integer.valueOf(matchingElements).equals(0)) {
            logError("ELEMENT NOT FOUND | " + identifiedElements);
            throw new AssertionError("ELEMENT NOT FOUND | " + identifiedElements);

        } else if (Integer.valueOf(matchingElements).equals(1)) {
            logStringIntoConsole("Only '1' matching element, performing 'hoverAndClick()'...");
            hoverAndClick(identifiedElements);

        } else {
            Random r = new Random();
            int randomValue = r.nextInt(matchingElements - 1);
            logStringIntoConsole("Random Int: " + randomValue);

            try {
                Actions builder = new Actions(driver);


                try {

                    builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
                } catch (StaleElementReferenceException ex) {
                    longWaitUntilLoaded();
                    builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
                }
                logStringIntoConsole("Clicked.");
                logStringIntoConsole("Element Text: " + elements(identifiedElements).get(randomValue).getText() + " | " + identifiedElements);

            } catch (StaleElementReferenceException e) {
                logStringIntoConsole("StaleElement *ATTEMPT TWO*");
                longWaitUntilLoaded();
                switchToDynamicFrame();

                Actions builder = new Actions(driver);

                try {

                    builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
                } catch (StaleElementReferenceException ex) {
                    longWaitUntilLoaded();
                    switchToDynamicFrame();
                    builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
                }
                logStringIntoConsole("Clicked.");
                logStringIntoConsole("Element Text: " + elements(identifiedElements).get(randomValue).getText() + " | " + identifiedElements);
            }
        }
    }

    public static void retryHoverAndClickRandomFromMultipleElements(By identifiedElements) {

        try {
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(identifiedElements));
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    elementToBeClickable(identifiedElements));
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();

        } catch (TimeoutException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();
        }

        int matchingElements = elements(identifiedElements).size();
        logStringIntoConsole("Matching # of Elements: " + matchingElements);


        if (Integer.valueOf(matchingElements).equals(0)) {
            logError("ELEMENT NOT FOUND | " + identifiedElements);
            throw new AssertionError("ELEMENT NOT FOUND | " + identifiedElements);

        } else if (Integer.valueOf(matchingElements).equals(1)) {
            logStringIntoConsole("Only '1' matching element, performing 'hoverAndClick()'...");
            retryFindClick(identifiedElements);

        } else {
            Random r = new Random();
            int randomValue = r.nextInt(matchingElements - 1);
            logStringIntoConsole("Random Int: " + randomValue);

            try {
                Actions builder = new Actions(driver);
                WebElement element = elements(identifiedElements).get(randomValue);

                builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
                waitUntilLoaded();
                builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();

                logStringIntoConsole("Element Text: " + element.getText() + " | " + identifiedElements);
            } catch (StaleElementReferenceException e) {
                logStringIntoConsole("StaleElement *ATTEMPT TWO*");
                longWaitUntilLoaded();
                switchToDynamicFrame();

                Actions builder = new Actions(driver);
                microSleep();
                builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
                waitUntilLoaded();
                WebElement element = elements(identifiedElements).get(randomValue);
                logStringIntoConsole("Element Text: " + element.getText() + " | " + identifiedElements);
            }
        }

        logStringIntoConsole("Retrying Click. | " + identifiedElements);
    }

    public static String hoverAndClickRandomFromMultipleElementsReturnSelection(By identifiedElements) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(identifiedElements));
        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                elementToBeClickable(identifiedElements));

        int matchingElements = elements(identifiedElements).size();
        logStringIntoConsole("Matching # of Elements: " + matchingElements);
        if (Integer.valueOf(matchingElements).equals(0)) {
            logError("ELEMENT ID ERROR!");
        }

        Random r = new Random();
        int randomValue = r.nextInt(matchingElements - 1);
        logStringIntoConsole("Random Int: " + randomValue);

        try {
            Actions builder = new Actions(driver);
            microSleep();
            builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
            microSleep();

        } catch (StaleElementReferenceException e) {
            logStringIntoConsole("StaleElement *ATTEMPT TWO*");
            longWaitUntilLoaded();
            switchToDynamicFrame();


            Actions builder = new Actions(driver);
            microSleep();
            builder.moveToElement(elements(identifiedElements).get(randomValue)).click().build().perform();
            microSleep();
        }

        WebElement element = elements(identifiedElements).get(randomValue);
        String selectedElement = element.getText();
        logStringIntoConsole("Element Text: " + selectedElement + " | " + identifiedElements);
        return selectedElement;
    }

    public static void ifPresentHoverAndClickRandomFromMultipleElements(By identifiedElements) {
        setDriverToConfigWaitingTime();

        switchToDynamicFrame();
        if (doesElementExist(identifiedElements)) {
            hoverAndClickRandomFromMultipleElements(identifiedElements);
        } else {
            logStringIntoConsole("Elements NOT present, continuing...");
        }

        setDriverToConfigWaitingTime();
    }

    public static void ifPresentHoverAndClick(By identifiedElements) {
        setDriverToConfigWaitingTime();

        switchToDynamicFrame();
        if (doesElementExist(identifiedElements)) {
            hoverAndClick(identifiedElements);
        } else {
            logStringIntoConsole("Elements NOT present, continuing...");
        }

        setDriverToConfigWaitingTime();
    }

    public static void hoverAndClick(By elementIdentifier, int xOffset, int yOffset) {


        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));

        highlightElement(elementIdentifier);

        Actions builder = new Actions(driver);

        try {
            builder.moveToElement(element(elementIdentifier), xOffset, yOffset).click().build().perform();
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();

            builder.moveToElement(element(elementIdentifier), xOffset, yOffset).click().build().perform();
        }


        logStringIntoConsole("Clicked. | " + elementIdentifier);

    }


    public static void retryHoverAndClick(By elementIdentifier) {


        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        retryFindClick(elementIdentifier);


    }

    public static String getTextFromElement(By elementIdentifier) {
        String text = element(elementIdentifier).getText();
        logStringIntoConsole("Element Text: " + text);

        return text;
    }

    public static By returnXpathFoundFromText(String textToSearchFor) {
        By element = By.xpath("//*[contains(.,'" + textToSearchFor + "')][not(.//*[contains(., '" + textToSearchFor + "')])]");
        return element;

    }


    /**
     * This method is used for wait for element for ELEMENT_POLL_TIME defined in
     * TestProperty file
     *
     * @param elementLocator
     * @param driver
     */
    public static void waitForElementToBeVisible(By elementLocator, WebDriver driver) {
        try {
            WebElement myDynamicElement = (new WebDriverWait(driver, WAITING_TIME))
                    .until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        } catch (Exception e) {
            logStringIntoConsole("Exception occurred.. " + e);
        }
    }

    public static void waitUntilLoaded() {
        //If present, accept any alerts
        ifPresentAcceptAlert();

        //Wait until page loaded
        waitUntilPageLoaded();
    }

    public static void longWaitUntilLoaded() {
        shortSleep();

        shortSleep();
    }


    private static void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait;
        wait = new WebDriverWait(driver, WAITING_TIME);
        wait.until(pageLoadCondition);
    }


    public static boolean waitForElementToDisappear(By elementLocator, int timeToWait) {
        boolean isElementInvisible = (new WebDriverWait(driver, timeToWait))
                .until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
        if (!isElementInvisible) {
            logError("Element still visible after '" + timeToWait + "'sec | " + elementLocator);
        }


        return isElementInvisible;
    }

    public static ArrayList<Integer> generateUniqueRandomNumber(int endPoint) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= endPoint; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        return list;

    }

    public static Boolean checkTextPresentInPage(String text, WebDriver driver) {
        Boolean textState = false;
        if (element(By.tagName("body")).getText().contains(text)) {
            textState = true;
        }
        return textState;
    }

    public static boolean findElementByText(String textToLookFor) {
        setDriverToConfigWaitingTime();


        By textContains = By.xpath("//*[contains(.,'" + textToLookFor + "')][not(.//*[contains(., '" + textToLookFor + "')])]");

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(textContains));
        logStringIntoConsole("Found text: " + textToLookFor);


        setDriverToConfigWaitingTime();
        return true;
    }

    public static By returnElement(String textToLookFor) {


        By textContains = By.xpath("//*[contains(.,'" + textToLookFor + "')][not(.//*[contains(., '" + textToLookFor + "')])]");

        return textContains;
    }

    public static boolean verifyElementAttributeValue(By element, String expectedValue) {
        if (element(element).getAttribute("value").equalsIgnoreCase(expectedValue)) {
            logStringIntoConsole("*** SUCCESS - VALUES MATCH ***");
            logStringIntoConsole("|*| EXPECTED Value: " + expectedValue);
            logStringIntoConsole("|*| ACTUAL Value: " + element(element).getAttribute("value"));
            return true;
        } else {
            logStringIntoConsole("*** ERROR - NO MATCH ***");
            logStringIntoConsole("|*| EXPECTED Value: " + expectedValue);
            logStringIntoConsole("|*| ACTUAL Value: " + element(element).getAttribute("value"));
            return false;
        }

    }

    public static boolean isTextDisplayed(String textToLookFor) {
        boolean result = false;
        setDriverToConfigWaitingTime();
        By textContains = By.xpath("(//*[contains(.,'" + textToLookFor + "')][not(.//*[contains(., '" + textToLookFor + "')])])[1]");
        try {
            if (element(textContains).isDisplayed()) {
                logStringIntoConsole("Yes, '" + textToLookFor + "' is displayed.");
                result = true;
            } else {
                logStringIntoConsole("No, '" + textToLookFor + "' is *NOT* displayed.");
                result = false;
            }
        } catch (Exception e) {
        }


        setDriverToConfigWaitingTime();
        return result;
    }

    public static void clickOnElementFromText(String textToClick) {

        By textContent = By.xpath("//*[contains(.,'" + textToClick + "')][not(.//*[contains(., '" + textToClick + "')])]");

        hoverAndClick(textContent);
    }

    public static By getElementFromText(String elementText) {
        By element = By.xpath("(//*[contains(.,'" + elementText + "')][not(.//*[contains(., '" + elementText + "')])])[1]");
        logStringIntoConsole("Element Retrieved From Text: " + element);

        return element;
    }

    public static String retrieveDropdownListOptions(By elementIdentifier) {

        List<String> selectOptions = new ArrayList<String>();
        Select dropdown = new Select(element(elementIdentifier));

        for (WebElement option : dropdown.getOptions()) {
            String txt = option.getText();
            if (option.getAttribute("value") != "") selectOptions.add(option.getText());
        }

        String listString = String.join(",", selectOptions);
        logStringIntoConsole("List of Dropdown Options: " + listString);
        return listString;
    }

    public static void selectDropdownByText(By elementIdentifier, String text) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        Select dropdown = new Select(element(elementIdentifier));
        try {
            dropdown.selectByVisibleText(text);
        } catch (NoSuchElementException e) {
            logError("Couldn't find drop-down option. Bypass via 'selectRandomDropdownExcludeInvalidFirstOption'");
            selectRandomDropdownExcludeInvalidFirstOption(elementIdentifier);
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();
            dropdown.selectByVisibleText(text);
        } catch (Exception e) {
            logError(e.toString());
        }


        logStringIntoConsole("DROPDOWN CHOICE: " + text + " | " + elementIdentifier);
    }

    public static String getSelectedDropdownValue(By elementIdentifier) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        Select select = new Select(element(elementIdentifier));
        WebElement option = select.getFirstSelectedOption();
        String selectedItem = option.getText();
        logStringIntoConsole("DROPDOWN CHOICE: " + selectedItem);

        return selectedItem;
    }

    public static void selectDropdownByPartialText(By elementIdentifier, String partialText) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        Select dropdown = new Select(element(elementIdentifier));
        for (WebElement option : dropdown.getOptions()) {
            String txt = option.getAttribute("value");
            if (doesStringContainSomeText(txt, partialText)) {
                dropdown.selectByVisibleText(txt);
            }
        }


    }


    public static void selectDropdownByIndex(By elementIdentifier, int index) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        Select dropdown = new Select(element(elementIdentifier));
        dropdown.selectByIndex(index);

        logStringIntoConsole("DROPDOWN index: " + index + " | " + elementIdentifier);
    }

    public static void scrollToElement(By elementIdentifier) {
        scrollToTopOfPage(driver);


        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));
        WebElement element = element(elementIdentifier);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToWebElement(WebElement elementToken, WebDriver driver) {
        scrollToTopOfPage(driver);


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementToken);
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void microSleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void click(By elementIdentifier) {
        hoverAndClick(elementIdentifier);
    }

    public static void switchToFrame(By elementToken) {
        try {
            setDriverToConfigWaitingTime();
            switchToDefaultContent();
            driver.switchTo().frame(element(elementToken));
        } catch (Exception e) {
            logStringIntoConsole("COULD NOT SWITCH TO DYNAMIC FRAME...proceeding");
        }

        setDriverToConfigWaitingTime();
    }

    public static void switchToDefaultContent() {
        setDriverToConfigWaitingTime();

        microSleep();
        driver.switchTo().defaultContent();
        microSleep();

        setDriverToConfigWaitingTime();
    }

    public static void shortSleep() {
        try {
            waitUntilLoaded();
            Thread.sleep(125);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }
    }

    public static void selectRandomDropdown(By elementIdentifier) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        logStringIntoConsole("*Selecting RANDOM Dropdown* | " + elementIdentifier);
        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));

        WebElement drpDwnList = element(elementIdentifier);
        Select objSel = new Select(drpDwnList);
        List<WebElement> weblist = objSel.getOptions();
        int iCnt = weblist.size();
        Random num = new Random();
        int iSelect = num.nextInt(iCnt);

        objSel.selectByIndex(iSelect);
        logStringIntoConsole("DROPDOWN CHOICE: " + drpDwnList.getAttribute("value"));


    }

    public static void selectRandomDropdownExcludeInvalidFirstOption(By elementIdentifier) {

        logStringIntoConsole("*Selecting RANDOM Dropdown* | " + elementIdentifier);
        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));


        WebElement drpDwnList = element(elementIdentifier);
        Select objSel = new Select(drpDwnList);
        List<WebElement> weblist = objSel.getOptions();
        int iCnt = weblist.size();
        Random num = new Random();
        int iSelect = num.nextInt(iCnt);

        try {
            if (Integer.valueOf(iSelect).equals(0)) {
                objSel.selectByIndex(iSelect + 1);
            } else {
                objSel.selectByIndex(iSelect);
            }
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();

            try {
                if (Integer.valueOf(iSelect).equals(0)) {
                    objSel.selectByIndex(iSelect + 1);
                } else {
                    objSel.selectByIndex(iSelect);
                }
            } catch (StaleElementReferenceException ex) {
                longWaitUntilLoaded();
            }
        }


        waitUntilLoaded();
        try {
            logStringIntoConsole("DROPDOWN CHOICE: " + drpDwnList.getAttribute("value"));
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();
            try {
                logStringIntoConsole("DROPDOWN CHOICE: " + drpDwnList.getAttribute("value"));
            } catch (StaleElementReferenceException ex) {
                longWaitUntilLoaded();
            }
        }
    }

    public static void ifPresentSelectRandomDropdownExcludeInvalidFirstOption(By elementIdentifier) {
        setDriverToConfigWaitingTime();

        switchToDynamicFrame();
        if (doesElementExist(elementIdentifier)) {

            logStringIntoConsole("*Selecting RANDOM Dropdown* | " + elementIdentifier);
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));


            WebElement drpDwnList = element(elementIdentifier);
            Select objSel = new Select(drpDwnList);
            List<WebElement> weblist = objSel.getOptions();
            int iCnt = weblist.size();
            Random num = new Random();
            int iSelect = num.nextInt(iCnt);
            try {
                if (Integer.valueOf(iSelect).equals(0)) {
                    objSel.selectByIndex(iSelect + 1);
                } else {
                    objSel.selectByIndex(iSelect);
                }
            } catch (StaleElementReferenceException e) {
                longWaitUntilLoaded();
                switchToDynamicFrame();

                if (Integer.valueOf(iSelect).equals(0)) {
                    objSel.selectByIndex(iSelect + 1);
                } else {
                    objSel.selectByIndex(iSelect);
                }
            }

            logStringIntoConsole("DROPDOWN CHOICE: " + drpDwnList.getAttribute("value"));
        }

        setDriverToConfigWaitingTime();
    }

    public static void selectRandomCheckbox(By elementIdentifier) {
        logStringIntoConsole("Selecting Random Checkbox | Element: " + elementIdentifier);

        try {
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));


            List<WebElement> oCheckBox = elements(elementIdentifier);
            Random num = new Random();
            int iCnt = num.nextInt(oCheckBox.size());
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    elementToBeClickable(oCheckBox.get(iCnt)));


            oCheckBox.get(iCnt).click();
        } catch (StaleElementReferenceException e) {
            longWaitUntilLoaded();
            switchToDynamicFrame();

            ifPresentSelectRandomCheckbox(elementIdentifier);
        } catch (Exception e) {
            ifPresentSelectRandomCheckbox(elementIdentifier);
        }
    }

    public static void ifPresentSelectRandomCheckbox(By elementIdentifier) {
        setDriverToConfigWaitingTime();

        switchToDynamicFrame();
        if (doesElementExist(elementIdentifier)) {
            logStringIntoConsole("Selecting Random Checkbox | Element: " + elementIdentifier);

            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    visibilityOfElementLocated(elementIdentifier));


            List<WebElement> oCheckBox = elements(elementIdentifier);
            Random num = new Random();
            int iCnt = num.nextInt(oCheckBox.size());
            new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                    elementToBeClickable(oCheckBox.get(iCnt)));

            try {

                oCheckBox.get(iCnt).click();
            } catch (StaleElementReferenceException e) {
                longWaitUntilLoaded();
                switchToDynamicFrame();

                oCheckBox.get(iCnt).click();
            }
        } else {
            logStringIntoConsole("Element not present, skipping... | " + elementIdentifier);
        }

        setDriverToConfigWaitingTime();
    }

    public static void selectRandomDropdown(By dropDown, String type, WebDriver driver) {
        String dropDownText = "no";
        Random randomOption = new Random();
        if (type.contains("Select")) {
            Select randomDrop = new Select(element(dropDown));
            int option = randomOption.nextInt(randomDrop.getOptions().size() - 1);
            randomDrop.selectByIndex(option);
            dropDownText = element(dropDown).getText();
        } else if (type.contains("List")) {
            List<WebElement> randomDrop = elements(dropDown);
            int option = randomOption.nextInt(randomDrop.size() - 1);
            randomDrop.get(option).click();
            dropDownText = randomDrop.get(option).getText();
        } else {
            logStringIntoConsole("Please check the dropdown parameters entered");
        }
        logStringIntoConsole("Clicked on the " + dropDownText + " dropdown option.");
    }

    public static void clickByJS(By elementIdentifier) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element(elementIdentifier));
        longWaitUntilLoaded();


        logStringIntoConsole("Clicked BY JS | " + elementIdentifier);
    }

    public static void clickByJS(WebElement elementIdentifier) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", elementIdentifier);
        longWaitUntilLoaded();

        logStringIntoConsole("Clicked BY JS | " + elementIdentifier);
    }

    public static void clickbyJSID(String ID, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(String.format("document.getElementById('%s').click()", ID));
    }

    public static void selectDateJS(String jsElemetLocation, String date, WebDriver driver) {
        JavascriptExecutor js;
        js = (JavascriptExecutor) driver;
        SeleniumUtil.sleep(2);
        if (date != null && !date.equals("")) {
            js.executeScript(jsElemetLocation.replaceAll("%s", date));
        }
    }

    public static void waitForElementToLoad(int maxWaitTime, By elementIdentifier) {


        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));
        logStringIntoConsole("Element has loaded.");
    }

    public static void waitForElementToBeClickable(int maxWaitTime, By elementIdentifier) {

        new WebDriverWait(driver, WAITING_TIME).until(ExpectedConditions.
                visibilityOfElementLocated(elementIdentifier));

        logStringIntoConsole("Element has loaded and is clickable.");
    }


    private static void waitUntilPageLoaded() {
        try {
            new WebDriverWait(driver, WAITING_TIME).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        } catch (Exception error) {
            logError("Page **NOT** loaded after waiting '" + WAITING_TIME + "' seconds! | 'waitUntilPageLoaded' not finished.");
        }
    }

    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static void scrollToTopOfPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({ top: 0, behavior: 'smooth' })", "");
        logStringIntoConsole("SCROLLED to the top of the page.");
    }

    public static void scrollToBottomOfPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)", "");
        logStringIntoConsole("SCROLLED to the top of the page.");
    }

    public static void clickOnFirstElementInRelatedGroup(List<WebElement> element) {


		/*List<WebElement> elements;
		elements = elements(element);*/

        element.get(0).click();
		/*Random num = new Random();
		int iCnt = num.nextInt(random.size());
		new WebDriverWait(driver,WAITING_TIME).until(ExpectedConditions.
				elementToBeClickable(random.get(iCnt)));
		random.get(iCnt).click();
		dropDownText = random.get(iCnt).getText();*/
        logStringIntoConsole("Clicked on the first element in an element identifier that returns more than one.");

    }

    public static void ifPresentAcceptAlert() {
        try {
            setDriverToConfigWaitingTime();
            WebDriverWait wait = new WebDriverWait(driver, 1);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            logError(alert.getText());
            alert.accept();
        } catch (Exception e) {
        }


        setDriverToConfigWaitingTime();
    }

    public static boolean isElementSelected(By element) {
        return element(element).isSelected();
    }

    public static boolean isElementSelected(WebElement element) {

        return element.isSelected();
    }

    public static void refreshPage() {
        logStringIntoConsole("** Refreshing Page **");
        driver.navigate().refresh();

    }
}
