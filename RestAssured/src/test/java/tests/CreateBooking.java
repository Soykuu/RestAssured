package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateBooking {

    @Test
    public void CreateBooking(){

        //Body oluşturma

        JSONObject body = new JSONObject();
        body.put("firstname", "Öykü");   // 1. parametre
        body.put("lastname","Demirel");  // 2. parametre
        body.put("totalprice",300);      // 3. parametre
        body.put("depositpaid", true);   // 4. parametre

        JSONObject bookingDates = new JSONObject(); //"bookingdates" parametresi checkin ve checkout olarak 2 değer tutuyor
        bookingDates.put("checkin","2023-04-04");
        bookingDates.put("checkout","2023-04-14");

        body.put("bookingdates",bookingDates);  // 5. parametre
        body.put("additionalneeds","Evcil hayvan kabul eden işletme");  // 6. parametre


        //Cagrıyı gerçekleştirme

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("https://restful-booker.herokuapp.com/booking");

        response.prettyPrint();


        //Assertionları yaz

        Assertions.assertEquals("Öykü",response.jsonPath().getJsonObject("Booking.firstname"));
        Assertions.assertEquals("Demirel",response.jsonPath().getJsonObject("Booking.lastname"));
        Assertions.assertEquals(true,response.jsonPath().getJsonObject("Booking.depositpaid"));
        Assertions.assertEquals(300,(Integer) response.jsonPath().getJsonObject("Booking.totalprice"));
        /*Integer totalPrice = response.jsonPath().getJsonObject("Booking.totalprice");
        Assertions.assertEquals(300, totalPrice);*/

    }
}
