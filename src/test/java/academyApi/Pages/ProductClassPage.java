package academyApi.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductClassPage extends BasePage {

    @FindBy(css = ".summary .price .woocommerce-Price-amount")
    private WebElement _price;

    @FindBy(css = ".input-text")
    private WebElement _quantityOfTheProductTextBox;

    @FindBy(css = ".single_add_to_cart_button")
    private WebElement _addToCartButton;

    @FindBy(css = ".woocommerce-message")
    private WebElement _successfulMessage;

    public ProductClassPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public ProductClassPage callingPageFactrory() {
        PageFactory.initElements(driver, ProductClassPage.class);
        return this;
    }

    public String verifyTheTitleOfThePage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }

    public String getTheProductPrice() {
        String price = _price.getText();
        return price;
    }

    public ProductClassPage increaseTheQuantityOfTheProduct(String number) {
        _quantityOfTheProductTextBox.clear();
        _quantityOfTheProductTextBox.sendKeys(number);
        return this;
    }

    public ProductClassPage addToCart() {
        _addToCartButton.click();
        return this;
    }

    public String verifyTheProductWasAdded() {
        String message = _successfulMessage.getText();
        return message;
    }

}
