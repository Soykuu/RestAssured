package tests;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Booking;
import models.BookingResponse;
import models.BookingDates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class CreateBooking_AfterBaseTest extends BaseTest {  //extend dediğimiz için bodyi direkt methodla oluşturur

    @Test
    public void CreateBooking_AfterBaseTest (){

        /*
        Cagrıyı gerçekleştirme

        createBooking() içerisinde BookingBody()'i de çağırıyoruz.
        Body oluşturmak için bir daha kod yazmadık.
        */

        Response response = createBooking();

        //Assertionları yaz
        Assertions.assertEquals("Öykü",response.jsonPath().getJsonObject("Booking.firstname"));
        Assertions.assertEquals("Demirel",response.jsonPath().getJsonObject("Booking.lastname"));
        Assertions.assertEquals(true,response.jsonPath().getJsonObject("Booking.depositpaid"));
        Assertions.assertEquals(300,(Integer) response.jsonPath().getJsonObject("Booking.totalprice"));

    }

    @Test
    public void CreateBookingWithPojo(){
        BookingDates bookingDates = new BookingDates("2023-04-30","2023-05-05");
        Booking booking = new Booking("Udemy", "Kus", 500,false, bookingDates,"Balkonlu oda istiyorum.");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/booking");

        response
                .then()
                .statusCode(200);

        BookingResponse bookingResponse = response.as(BookingResponse.class);
        System.out.println(bookingResponse + " nolu booking response kaydedildi.");

        Assertions.assertEquals("Udemy",bookingResponse.getBooking().getFirstname());
        Assertions.assertEquals("Kus",bookingResponse.getBooking().getLastname());
        Assertions.assertEquals(500,bookingResponse.getBooking().getTotalprice());
        Assertions.assertEquals(false,bookingResponse.getBooking().isDepositpaid());
        Assertions.assertEquals("2023-04-30",bookingResponse.getBooking().getBookingdates().getCheckin());
        Assertions.assertEquals("2023-05-05",bookingResponse.getBooking().getBookingdates().getCheckout());
    }
}
