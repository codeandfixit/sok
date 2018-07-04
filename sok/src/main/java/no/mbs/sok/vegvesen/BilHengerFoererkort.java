package no.mbs.sok.vegvesen;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 12.11.2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "forerkortklasse",
        "bil",
        "tilhenger"
})
public class BilHengerFoererkort {

    @JsonProperty("forerkortklasse")
    private String forerkortklasse;
    @JsonProperty("bil")
    private Bil bil;
    @JsonProperty("tilhenger")
    private Tilhenger tilhenger;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("forerkortklasse")
    public String getForerkortklasse() {
        return forerkortklasse;
    }

    @JsonProperty("forerkortklasse")
    public void setForerkortklasse(String forerkortklasse) {
        this.forerkortklasse = forerkortklasse;
    }

    @JsonProperty("bil")
    public Bil getBil() {
        return bil;
    }

    @JsonProperty("bil")
    public void setBil(Bil bil) {
        this.bil = bil;
    }

    @JsonProperty("tilhenger")
    public Tilhenger getTilhenger() {
        return tilhenger;
    }

    @JsonProperty("tilhenger")
    public void setTilhenger(Tilhenger tilhenger) {
        this.tilhenger = tilhenger;
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
