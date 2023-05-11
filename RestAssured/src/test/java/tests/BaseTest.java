package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class BaseTest {

    RequestSpecification spec;

    @BeforeEach
    public void setup(){

        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .build();

    }

    //setup class'ında tanımladığımız methodları aşağıdaki classlar için kullanacağız;

    protected String bookingBody(String firstname,String lastname,int totalprice, boolean depositpaid){

        JSONObject body = new JSONObject();
        body.put("firstname", firstname);       // 1. parametre
        body.put("lastname",lastname);          // 2. parametre
        body.put("totalprice",totalprice);      // 3. parametre
        body.put("depositpaid", depositpaid);   // 4. parametre

        JSONObject bookingDates = new JSONObject(); //"bookingdates" parametresi checkin ve checkout olarak 2 değer tutuyor
        bookingDates.put("checkin","2023-04-04");
        bookingDates.put("checkout","2023-04-14");

        body.put("bookingdates",bookingDates);  // 5. parametre
        body.put("additionalneeds","Evcil hayvan kabul eden işletme");  // 6. parametre

        return body.toString();
    }

    protected Response createBooking(){

        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .body(bookingBody("Öykü","Demirel",300,true))
                //.post("https://restful-booker.herokuapp.com/booking");
                .post("/Booking"); //URL setup class'ındaki setBaseUri() methodu ile geliyor, sadece uzantıyı yazıyoruz "/Booking"

        //response.prettyPrint(); -- Response logunu yine setup classındaki addFilters() methodu ile alıyoruz
        response
                .then()
                .statusCode(200);

        return response;
    }

    protected String createToken(){

        JSONObject body = new JSONObject();
        body.put("username", "admin");
        body.put("password", "password123");

        Response response = given(spec)
                .when()
                .contentType(ContentType.JSON)
                .body(body.toString())
                //.log().all() -- çağrı öncesinde yaptığımız loglama - setup classından addFilters methodu ile req loguna ulaşıyoruz
                .post("/auth"); //URL setup class'ındaki setBaseUri() methodu ile geliyor

        return response.jsonPath().getJsonObject("token");
    }


    protected int createBookingID(){

        Response response = createBooking();
        return response.jsonPath().getJsonObject("bookingid");
    }

}
