package apiAffiliated;

import com.fasterxml.jackson.annotation.JsonProperty;



public class BookingResponse {

    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private int totalprice;
    @JsonProperty
    private boolean depositpaid;
    @JsonProperty
    private BookingDates bookingdates;
    @JsonProperty
    private String additionalneeds = "Breakfast";

    public String getAdditionalneeds() {
        return additionalneeds;
    }
}
