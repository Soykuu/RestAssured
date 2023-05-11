package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class DeleteBooking extends BaseTest {

    @Test
    public void DeleteBooking(){
        //Token oluşturma -- createToken() metodundan alıyoruz
        //Rezervasyon oluşturma -- Silmek için Booking ID lazım, createBookingID() metodundan alıyoruz

        //Delete çağrısı yapma
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+createToken())
                .when()
                .delete("/Booking/"+createBookingID()); //URL BaseTest - setup class'ındaki setBaseUri() methodu ile geliyor

        response.prettyPrint();
        response
                .then()
                .statusCode(201);

        //Assertion olarak responsedaki statü kodunu kontrol ediyoruz. (201)
    }
}
