package academyApi.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {
    @FindBy(css = ".product-name[data-title]")
    private WebElement _product;

    @FindBy(css = ".product-price[data-title]")
    private WebElement _priceOfTheProduct;

    @FindBy(css = ".input-text[inputmode]")
    private WebElement _quantityOfTheProduct;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String verifyIfTheProductExistsAndGetTheName() {
        _product.isDisplayed();
        String nameOfTheProduct = _product.getText();
        return nameOfTheProduct;
    }

    public String verifyIfThePriceIsTheSameAndGetThePrice(){
        _priceOfTheProduct.isDisplayed();
        String price = _priceOfTheProduct.getText();
        return price;
    }

    public String verifyIfTheQuantityExistsAndGetTheNumber(){
        _quantityOfTheProduct.isDisplayed();
        String quantity = _quantityOfTheProduct.getAttribute("value");
        return quantity;
    }
}
