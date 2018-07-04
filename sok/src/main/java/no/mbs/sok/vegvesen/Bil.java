package no.mbs.sok.vegvesen;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "regnr",
        "tekniskkjoretoygruppe",
        "maksBelastningTilhengerKopling",
        "totalvekt",
        "erAvregistrert",
        "vogntogvekt",
        "nyttelast",
        "egenvekt",
        "maksTilhengervektMedBremser",
        "maksTilhengervektUtenBremser"
})
public class Bil {

    @JsonProperty("regnr")
    private String regnr;
    @JsonProperty("tekniskkjoretoygruppe")
    private String tekniskkjoretoygruppe;
    @JsonProperty("maksBelastningTilhengerKopling")
    private Integer maksBelastningTilhengerKopling;
    @JsonProperty("totalvekt")
    private Integer totalvekt;
    @JsonProperty("erAvregistrert")
    private Boolean erAvregistrert;
    @JsonProperty("vogntogvekt")
    private Integer vogntogvekt;
    @JsonProperty("nyttelast")
    private Integer nyttelast;
    @JsonProperty("egenvekt")
    private Integer egenvekt;
    @JsonProperty("maksTilhengervektMedBremser")
    private Integer maksTilhengervektMedBremser;
    @JsonProperty("maksTilhengervektUtenBremser")
    private Integer maksTilhengervektUtenBremser;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("regnr")
    public String getRegnr() {
        return regnr;
    }

    @JsonProperty("regnr")
    public void setRegnr(String regnr) {
        this.regnr = regnr;
    }

    @JsonProperty("tekniskkjoretoygruppe")
    public String getTekniskkjoretoygruppe() {
        return tekniskkjoretoygruppe;
    }

    @JsonProperty("tekniskkjoretoygruppe")
    public void setTekniskkjoretoygruppe(String tekniskkjoretoygruppe) {
        this.tekniskkjoretoygruppe = tekniskkjoretoygruppe;
    }

    @JsonProperty("maksBelastningTilhengerKopling")
    public Integer getMaksBelastningTilhengerKopling() {
        return maksBelastningTilhengerKopling;
    }

    @JsonProperty("maksBelastningTilhengerKopling")
    public void setMaksBelastningTilhengerKopling(Integer maksBelastningTilhengerKopling) {
        this.maksBelastningTilhengerKopling = maksBelastningTilhengerKopling;
    }

    @JsonProperty("totalvekt")
    public Integer getTotalvekt() {
        return totalvekt;
    }

    @JsonProperty("totalvekt")
    public void setTotalvekt(Integer totalvekt) {
        this.totalvekt = totalvekt;
    }

    @JsonProperty("erAvregistrert")
    public Boolean getErAvregistrert() {
        return erAvregistrert;
    }

    @JsonProperty("erAvregistrert")
    public void setErAvregistrert(Boolean erAvregistrert) {
        this.erAvregistrert = erAvregistrert;
    }

    @JsonProperty("vogntogvekt")
    public Integer getVogntogvekt() {
        return vogntogvekt;
    }

    @JsonProperty("vogntogvekt")
    public void setVogntogvekt(Integer vogntogvekt) {
        this.vogntogvekt = vogntogvekt;
    }

    @JsonProperty("nyttelast")
    public Integer getNyttelast() {
        return nyttelast;
    }

    @JsonProperty("nyttelast")
    public void setNyttelast(Integer nyttelast) {
        this.nyttelast = nyttelast;
    }

    @JsonProperty("egenvekt")
    public Integer getEgenvekt() {
        return egenvekt;
    }

    @JsonProperty("egenvekt")
    public void setEgenvekt(Integer egenvekt) {
        this.egenvekt = egenvekt;
    }

    @JsonProperty("maksTilhengervektMedBremser")
    public Integer getMaksTilhengervektMedBremser() {
        return maksTilhengervektMedBremser;
    }

    @JsonProperty("maksTilhengervektMedBremser")
    public void setMaksTilhengervektMedBremser(Integer maksTilhengervektMedBremser) {
        this.maksTilhengervektMedBremser = maksTilhengervektMedBremser;
    }

    @JsonProperty("maksTilhengervektUtenBremser")
    public Integer getMaksTilhengervektUtenBremser() {
        return maksTilhengervektUtenBremser;
    }

    @JsonProperty("maksTilhengervektUtenBremser")
    public void setMaksTilhengervektUtenBremser(Integer maksTilhengervektUtenBremser) {
        this.maksTilhengervektUtenBremser = maksTilhengervektUtenBremser;
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
