package no.mbs.sok.vegvesen;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Tilhenger {

    @JsonProperty("regnr")
    private String regnr;
    @JsonProperty("tilhengergruppe")
    private Integer tilhengergruppe;
    @JsonProperty("egenvekt")
    private Integer egenvekt;
    @JsonProperty("erAvregistrert")
    private Boolean erAvregistrert;
    @JsonProperty("maksBelastningTilhengerKopling")
    private Integer maksBelastningTilhengerKopling;
    @JsonProperty("totalvekt")
    private Integer totalvekt;
    @JsonProperty("nyttelast")
    private Integer nyttelast;
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

    @JsonProperty("tilhengergruppe")
    public Integer getTilhengergruppe() {
        return tilhengergruppe;
    }

    @JsonProperty("tilhengergruppe")
    public void setTilhengergruppe(Integer tilhengergruppe) {
        this.tilhengergruppe = tilhengergruppe;
    }

    @JsonProperty("egenvekt")
    public Integer getEgenvekt() {
        return egenvekt;
    }

    @JsonProperty("egenvekt")
    public void setEgenvekt(Integer egenvekt) {
        this.egenvekt = egenvekt;
    }

    @JsonProperty("erAvregistrert")
    public Boolean getErAvregistrert() {
        return erAvregistrert;
    }

    @JsonProperty("erAvregistrert")
    public void setErAvregistrert(Boolean erAvregistrert) {
        this.erAvregistrert = erAvregistrert;
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

    @JsonProperty("nyttelast")
    public Integer getNyttelast() {
        return nyttelast;
    }

    @JsonProperty("nyttelast")
    public void setNyttelast(Integer nyttelast) {
        this.nyttelast = nyttelast;
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

