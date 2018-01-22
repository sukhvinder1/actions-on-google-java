
package ca.sukhsingh.actions.on.google.request.originalrequest;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sukhSingh on 2017-08-09.
 */
public class User {

    @JsonProperty("userStorage")
    private String userStorage;
    @JsonProperty("lastSeen")
    private String lastSeen;
    @JsonProperty("profile")
    private Profile profile;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("permissions")
    private List<String> permissions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Profile getProfile() {
        return profile;
    }

    public String getLocale() {
        return locale;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserStorage() {
        return userStorage;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getPermissions() {
        return permissions;
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
