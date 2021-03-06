package io.appium.java_client.pagefactory_tests.widgets.combined.annotated;



import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.SelendroidFindBy;
import io.appium.java_client.pagefactory_tests.widgets.combined.simple.CombinedReview;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(id = "main_container")
@SelendroidFindBy(className = "android.widget.RelativeLayout")
@AndroidFindBys({@AndroidFindBy(id = "android:id/content"),
        @AndroidFindBy(className = "android.widget.RelativeLayout")})
public class AnnotatedCombinedReview extends CombinedReview {
    protected AnnotatedCombinedReview(WebElement element) {
        super(element);
    }
}
