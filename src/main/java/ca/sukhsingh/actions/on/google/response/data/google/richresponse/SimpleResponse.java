package ca.sukhsingh.actions.on.google.response.data.google.richresponse;

import ca.sukhsingh.actions.on.google.Util;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sukhsingh on 2017-08-14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleResponse {
    @JsonProperty("textToSpeech")
    private String textToSpeech;
    @JsonProperty("ssml")
    private String ssml;
    @JsonProperty("displayText")
    private String displayText;

    public SimpleResponse(String textToSpeech, String displayText) {
        if (Util.isSsml(textToSpeech)) {
            this.ssml = textToSpeech;
        } else {
            this.textToSpeech = textToSpeech;
        }
        this.displayText = displayText;
    }

    public SimpleResponse() {
    }

    public String getTextToSpeech() {
        return textToSpeech;
    }

    public void setTextToSpeech(String textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getSsml() {
        return ssml;
    }

    public void setSsml(String ssml) {
        this.ssml = ssml;
    }
}
