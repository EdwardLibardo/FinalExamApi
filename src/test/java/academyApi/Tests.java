package academyApi;

import academyApi.Pages.CartPage;
import academyApi.Pages.ProductClassPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

public class Tests {
    private String _url = "http://34.205.174.166/";
    private Response _response;
    private final String _username = "shopmanager";
    private final String _password = "axY2 rimc SzO9 cobf AZBw NLnX";
    private String _id;
    private final String _productName = "Edward";
    private final String _price = "$21.99";
    private final String _quantity = "7";
    WebDriver driver;
    ProductClassPage _productClassPage;
    CartPage _cartPage;

    @BeforeTest
    public void beforeTest() {
        driver = new ChromeDriver();
    }

    @Test
    public void shoppingSite() {
        _response = (Response) RestAssured.get(_url);
        Assert.assertEquals(_response.getStatusCode(), 200);
        //this json file is created to create the bug using a file if you want to use the json directly
        File jsonCreatAnIssue = new File("src/test/resources/creatingProduct.json");
        _response = RestAssured.given().auth().preemptive().basic(_username, _password)
                .contentType("application/json").body(jsonCreatAnIssue).when().post(_url + "wp-json/wc/v3/products");
        String[] body = _response.asString().split(":");
        //_id = _response.then().contentType("application/json").extract().path("id"); doesnt work
        String[] splitId = Arrays.toString(body).split(",");
        _id = splitId[1];
        System.out.println(_id);
        Assert.assertEquals(_response.getStatusCode(), 201);
        String productUrl = _url + "product/edward/";
        driver.get(productUrl);
        _productClassPage = new ProductClassPage(driver);
        String title = _productClassPage.verifyTheTitleOfThePage();
        Assert.assertEquals(productUrl, title);
        String price = _productClassPage.getTheProductPrice();
        Assert.assertEquals(price, _price);
        _productClassPage.increaseTheQuantityOfTheProduct(_quantity);
        _productClassPage.addToCart();
        String successMessage = _productClassPage.verifyTheProductWasAdded();
        Assert.assertTrue(successMessage.contains("have been added to your cart"));
        String cartUrl = _url + "cart/";
        driver.get(cartUrl);
        _cartPage = new CartPage(driver);
        String productName = _cartPage.verifyIfTheProductExistsAndGetTheName();
        Assert.assertEquals(productName, _productName);
        String verifyPrice = _cartPage.verifyIfThePriceIsTheSameAndGetThePrice();
        Assert.assertEquals(verifyPrice, _price);
        String quantity = _cartPage.verifyIfTheQuantityExistsAndGetTheNumber();
        Assert.assertEquals(quantity, _quantity);
        //To Delete the product
        // RequestSpecification request = RestAssured.given().auth().preemptive().basic(_username, _password);
        //request.header("Content-Type", "application/json");
        //_response = request.delete(_url + "wp-json/wc/v3/product/" + _id);
        _id = _id.replaceAll("\\s+", "");
        String deleteUrl = _url + "wp-json/wc/v3/product/" +_id;
        _response = RestAssured.given().auth().preemptive().basic(_username, _password).when()
                .delete(deleteUrl);
        Assert.assertEquals(_response.getStatusCode(), 200);
    }
}
