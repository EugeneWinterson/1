package ru.usb.springbootcbrfmpr.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.lang.reflect.Array;

@Data
public class PostSendData {

    String apikey;
    String action;
    String group;
    String sendwhen;
    Letter letter;
    @JsonProperty(value = "users.list")
    Object usersList;
    Object caption;
    Object rows;
}

class Letter {
    @JsonProperty(value = "draft.id")
    String draftId;
}

class UsersList {
    Caption caption;
}

class Caption {
    CaptionRow[] captionRow;
}

class CaptionRow {
    String anketa;
    String quest;
}