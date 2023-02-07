package ru.usb.springbootcbrfmpr.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public class SendSayLoginBody {
    @JsonProperty("request.id")
    String requestId;
    @JsonProperty("errors")
    Errors[] errors;
    @JsonProperty("duration")
    String duration;
    @JsonProperty("_ehid")
    String ehId;
}
class Errors {
    @JsonProperty("id")
    String id;
}