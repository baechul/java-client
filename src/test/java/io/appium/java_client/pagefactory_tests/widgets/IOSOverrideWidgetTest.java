package io.appium.java_client.pagefactory_tests.widgets;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IOSOverrideWidgetTest implements WidgetTest{

    private IOSDriver<?> driver;
    private static AppiumDriverLocalService service;
    private static RottenTomatoes rottenTomatoes;

    @BeforeClass
    public static void beforeClass() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @Before
    public void setUp() throws Exception {
        File appDir = new File("src/test/java/io/appium/java_client");
        File app = new File(appDir, "RottenTomatoes.zip");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        driver = new IOSDriver<>(service.getUrl(), capabilities);

        rottenTomatoes = new RottenTomatoes();
        PageFactory.initElements(new AppiumFieldDecorator(driver, new TimeOutDuration(5, TimeUnit.SECONDS)), rottenTomatoes);
    }

    @After
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (service != null)
            service.stop();
    }

    @Test
    @Override
    public void checkACommonWidget() {
        assertTrue(rottenTomatoes.getSimpleMovieCount() >= 1);
        Movie movie = rottenTomatoes.getASimpleMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoes.checkSimpleReview();
    }

    @Override
    @Test
    public void checkAnAnnotatedWidget() {
        assertTrue(rottenTomatoes.getAnnotatedMovieCount() >= 1);
        Movie movie = rottenTomatoes.getAnAnnotatedMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoes.checkAnnotatedReview();
    }


    @Override
    @Test
    public void checkAnExtendedWidget() {
        assertTrue(rottenTomatoes.getExtendeddMovieCount() >= 1);
        Movie movie = rottenTomatoes.getAnExtendedMovie(0);
        assertTrue(!StringUtils.isBlank(movie.title()));
        assertTrue(!StringUtils.isBlank(movie.score()));
        assertNotNull(movie.getPoster());
        movie.goToReview();

        rottenTomatoes.checkExtendedReview();
    }

    @Override
    @Test
    public void checkTheLocatorOverridingOnAWidget() {
        try {
            assertTrue(rottenTomatoes.getFakedMovieCount() == 0);
        }
        catch (Exception e){
            if (!NoSuchElementException.class.isAssignableFrom(e.getClass()))
                throw e;
        }

        rottenTomatoes.getASimpleMovie(0).goToReview();

        try {
            rottenTomatoes.checkFakeReview();
        }
        catch (Exception e){
            if (NoSuchElementException.class.isAssignableFrom(e.getClass()))
                return;
            else
                throw e;
        }
        throw new RuntimeException("Any exception was expected");
    }
}
