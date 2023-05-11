package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class UpdateBooking extends BaseTest {

    @Test
    public void UpdateBooking (){   //Put çağrılarında bodydeki tüm parametreleri göndermek gerekiyor, patch'de sadece istenilen alanları gönderiyoruz!

        //Token oluşturma  --- BaseTest içerisinde createToken() metodu oluşturduk
        String token = createToken();


        //Rezervasyon oluşturma
        Response createBookingID = createBooking();
        int bookingID = createBookingID.jsonPath().getJsonObject("bookingid");


        //Request yapma
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token)
                .body(bookingBody("Deniz","Test",700,false))
                .when()
                .put("/Booking/" +bookingID); //URL BaseTest - setup class'ındaki setBaseUri() methodu ile geliyor

        //response.prettyPrint(); -- setup classından addFilters methodu ile res loguna ulaşıyoruz


        //Assetions
        /* -BaseTest'te bookingBody alanındaki parametreleri dinamik hale getirdik
           -Booking oluşturmak için request yaparken bookingBody() metodu içerisine
            farklı değerler yazarak test edebiliriz, assertionlar yazabiliriz
         */

        String firstname = response.jsonPath().getJsonObject("firstname");
        Assertions.assertEquals("Deniz",firstname);

        String lastname = response.jsonPath().getJsonObject("lastname");
        Assertions.assertEquals("Test",lastname);

        int totalPrice = response.jsonPath().getJsonObject("totalprice");
        Assertions.assertEquals(700,totalPrice);

        boolean depositpaid = response.jsonPath().getJsonObject("depositpaid");
        Assertions.assertEquals(false, depositpaid);
        //Assertions.assertEquals(false,response.jsonPath().getJsonObject("depositpaid"));

    }

}
