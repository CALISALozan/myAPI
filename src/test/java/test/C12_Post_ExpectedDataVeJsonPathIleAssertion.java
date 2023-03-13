package test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class C12_Post_ExpectedDataVeJsonPathIleAssertion {
    /*
    https://restful-booker.herokuapp.com/booking url’ine
    asagidaki body'ye sahip bir POST request gonderdigimizde
    donen response’un id disinda asagidaki gibi oldugunu test edin.
                        Request body
                   {
                        "firstname" : "Ahmet",
                        "lastname" : “Bulut",
                        "totalprice" : 500,
                        "depositpaid" : false,
                        "bookingdates" : {
                                 "checkin" : "2021-06-01",
                                 "checkout" : "2021-06-10"
                                          },
                        "additionalneeds" : "wi-fi"
                    }
                        Response Body
                   {
                    "bookingid":24,
                    "booking":{
                        "firstname":"Ahmet",
                        "lastname":"Bulut",
                        "totalprice":500,
                        "depositpaid":false,
                        "bookingdates":{
                            "checkin":"2021-06-01",
                            "checkout":"2021-06-10"
                                        }
                        ,
                        "additionalneeds":"wi-fi"
                             }
                    }
         */


    @Test
    public void post01(){

        //1- URL ve Body hazırla
        String url="https://restful-booker.herokuapp.com/booking";

        JSONObject innerbody=new JSONObject();
        innerbody.put("checkin" , "2021-06-01");
        innerbody.put("checkout" , "2021-06-10");

        JSONObject reqbody=new JSONObject();
        reqbody.put("firstname" , "Ali");
        reqbody.put("lastname" , "Bak");
        reqbody.put("totalprice" , 500);
        reqbody.put("depositpaid" , false);
        reqbody.put("bookingdates" , innerbody);
        reqbody.put("additionalneeds" , "wi-fi");

       // System.out.println("reqbody= "+reqbody);

        //2- Expected Data hazırla
        JSONObject expbody=new JSONObject();

        expbody.put("bookingid",24);
        expbody.put("booking",reqbody);

      //  System.out.println("expbody " + expbody);

        //3-Response 'i kaydet
        Response response=given().
                contentType(ContentType.JSON).
                when().
                body(reqbody.toString()).
                post(url);

        //System.out.println("response " );
       // response.prettyPrint();
        //4- Assertion
        JsonPath resJsonPath=response.jsonPath();
        assertEquals(expbody.getJSONObject("booking").get("firstname"),resJsonPath.get("booking.firstname"));
        assertEquals(expbody.getJSONObject("booking").get("lastname"),resJsonPath.get("booking.lastname"));
        assertEquals(expbody.getJSONObject("booking").get("totalprice"),resJsonPath.get("booking.totalprice"));
        assertEquals(expbody.getJSONObject("booking").get("depositpaid"),resJsonPath.get("booking.depositpaid"));
        assertEquals(expbody.getJSONObject("booking").get("additionalneeds"),resJsonPath.get("booking.additionalneeds"));
        assertEquals(expbody.getJSONObject("booking").getJSONObject("bookingdates").get("checkin"),
                resJsonPath.get("booking.bookingdates.checkin"));
        assertEquals(expbody.getJSONObject("booking").getJSONObject("bookingdates").get("checkout"),
                resJsonPath.get("booking.bookingdates.checkout"));
    }
}
