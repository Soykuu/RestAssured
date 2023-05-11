package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetBooking extends BaseTest {

        @Test
        public void GetBooking(){

            given(spec)
                    .when()
                    .get("/Booking") //URL BaseTest - setup class'ındaki setBaseUri() methodu ile geliyor
                    .then()
                    //.log().all() -- çağrı öncesinde yaptığımız loglama - setup classından addFilters methodu ile req loguna ulaşıyoruz
                    .statusCode(200);

        }
        @Test
        public void GetBookingWithFirstnameFilter(){
                // Yrni rezervasyon oluşturma

                int bookingID = createBookingID();

                // Çağrıya query parametresi ekle

                spec.queryParam("firstname", "Öykü");
                spec.queryParam("lastname","Demirel");

                // Çağrıyı gerçekleştir

                Response res = given(spec)
                        .when()
                        .get("/Booking");

                // Assertion yaz

                res
                        .then()
                        .statusCode(200);

                List<Integer> list = res.jsonPath().getList("bookingid");
                System.out.println(list);
                Assertions.assertTrue(list.contains(bookingID));

        }

}
