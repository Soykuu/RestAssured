package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class GetBookingID extends BaseTest {
    //https://restful-booker.herokuapp.com/booking/3030
    // 1688 nolu bookingi bir önceki classın sonucunda listelenen id'lerden aldık
    // bu tesleri çalıştırmak için GetBooking classtan yeni id al

    // BaseTest classında createBookingID methodu ile dinamik halde getirdiğimiz için manuel olarak id kopyalamamıza gerek kalmadı
    // ancak bu classı düzenlemedim, manuel bıraktım !!!

    @Test
    public void GetBookingByID(){

        given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/1688")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void GetBookingByID2(){

        Response response = given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking/1688");

        response
                .then()
                .statusCode(200);

        response.prettyPrint();  //response body'i görmek için prettyPrint() methodunu çağırıyoruz.


        // Assertions
        Assertions.assertEquals("John",response.jsonPath().getJsonObject("firstname"));
        Assertions.assertEquals("Smith",response.jsonPath().getJsonObject("lastname"));
        Assertions.assertEquals(true,response.jsonPath().getJsonObject("depositpaid"));
        /*Boolean paidDeposit = response.jsonPath().getJsonObject("depositpaid");
        Assertions.assertEquals(true,paidDeposit);*/

        Integer totalPrice = response.jsonPath().getJsonObject("totalprice");
        Assertions.assertEquals(111,totalPrice);
        //Assertions.assertEquals(111,(Integer) response.jsonPath().getJsonObject("totalprice"));

    }
}
