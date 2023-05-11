package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class GetBookingByID_AfterBaseTest extends BaseTest {

    @Test
    public void GetBookingByID_AfterBaseTest(){

        Response newBooking = createBooking(); //BaseTest classındaki BookingOluştur() methodu ile yeni bir Booking oluşturduk
        int rezervasyonID = newBooking.jsonPath().getJsonObject("bookingid"); //Oluşturduğumuz bookingdeki ID'yi rezervasyonID variable'ına atadık

        //sürekli ID'ler değiştiği için kendi oluşturduğumuz Booking'in ID'sini linke yazdırarak test ediyoruz
        Response response = given(spec)
                .when()
                .get("/Booking/" + rezervasyonID); //URL BaseTest - setup class'ındaki setBaseUri() methodu ile geliyor

        response
                .then()
                .statusCode(200);

        //response body'i görmek için prettyPrint() methodunu çağırıyoruz
        //response.prettyPrint(); -- setup classından addFilters methodu ile res loguna ulaşıyoruz


        //Assertions
        Assertions.assertEquals("Öykü",response.jsonPath().getJsonObject("firstname"));
        Assertions.assertEquals("Demirel",response.jsonPath().getJsonObject("lastname"));
        Assertions.assertEquals(true,response.jsonPath().getJsonObject("depositpaid"));

        Integer totalPrice = response.jsonPath().getJsonObject("totalprice");
        Assertions.assertEquals(300,totalPrice);


    }

}
