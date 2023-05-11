package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class PartialUpdateBooking extends BaseTest {

    @Test
    public void PartialUpdateBooking(){
        /*  Token oluşturma --Bu kodu sürekli yazmak yerine request yaparken header içerisinde direkt createToken() methodunu kullandık.

        String token = createToken();
         */

        /* Rezervasyon oluşturma --Bu kodu da sürekli yazmamak için createBookingID() olarak BaseTest altında method oluşturduk.

        Response newBooking = createBooking();
        int bookingID = newBooking.jsonPath().getJsonObject("bookingid");
         */


        //Request yapma
        JSONObject patchBody = new JSONObject();  //değiştirmek istediğimiz kısmı body olarak tanımlıyoruz
        patchBody.put("totalprice",450);

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+createToken())
                .body(patchBody.toString())
                .when()
                .patch("/Booking/"+createBookingID()); //URL BaseTest - setup class'ındaki setBaseUri() methodu ile geliyor

        //response.prettyPrint(); -- setup classından addFilters methodu ile res loguna ulaşıyoruz


        //Assertions
        Assertions.assertEquals("Öykü",response.jsonPath().getJsonObject("firstname"));
        Assertions.assertEquals(450,(Integer) response.jsonPath().getJsonObject("totalprice"));
    }
}
