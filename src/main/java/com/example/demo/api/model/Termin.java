package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/**
 * Auto-generated stub by org.openapitools:openapi-generator-maven-plugin:5.1.0
 * Termin
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-01T18:01:02.009225+02:00[Europe/Berlin]")
public class Termin   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("von")
  private String von;

  @JsonProperty("bis")
  private String bis;

  @JsonProperty("werkstattName")
  private String werkstattName;

  @JsonProperty("leistung")
  private String leistung;

  @JsonProperty("leistungsId")
  private String leistungsId;

  public Termin id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Eindeutige Id des Termins
   * @return id
  */
  @ApiModelProperty(value = "Eindeutige Id des Termins")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Termin von(String von) {
    this.von = von;
    return this;
  }

  /**
   * Termin beginn
   * @return von
  */
  @ApiModelProperty(value = "Termin beginn")


  public String getVon() {
    return von;
  }

  public void setVon(String von) {
    this.von = von;
  }

  public Termin bis(String bis) {
    this.bis = bis;
    return this;
  }

  /**
   * Termin ende
   * @return bis
  */
  @ApiModelProperty(value = "Termin ende")


  public String getBis() {
    return bis;
  }

  public void setBis(String bis) {
    this.bis = bis;
  }

  public Termin werkstattName(String werkstattName) {
    this.werkstattName = werkstattName;
    return this;
  }

  /**
   * Name der Werkstatt
   * @return werkstattName
  */
  @ApiModelProperty(value = "Name der Werkstatt")


  public String getWerkstattName() {
    return werkstattName;
  }

  public void setWerkstattName(String werkstattName) {
    this.werkstattName = werkstattName;
  }

  public Termin leistung(String leistung) {
    this.leistung = leistung;
    return this;
  }

  /**
   * Beschreibung der Leistung
   * @return leistung
  */
  @ApiModelProperty(value = "Beschreibung der Leistung")


  public String getLeistung() {
    return leistung;
  }

  public void setLeistung(String leistung) {
    this.leistung = leistung;
  }

  public Termin leistungsId(String leistungsId) {
    this.leistungsId = leistungsId;
    return this;
  }

  /**
   * Id der Leistung
   * @return leistungsId
  */
  @ApiModelProperty(value = "Id der Leistung")


  public String getLeistungsId() {
    return leistungsId;
  }

  public void setLeistungsId(String leistungsId) {
    this.leistungsId = leistungsId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Termin termin = (Termin) o;
    return Objects.equals(this.id, termin.id) &&
        Objects.equals(this.von, termin.von) &&
        Objects.equals(this.bis, termin.bis) &&
        Objects.equals(this.werkstattName, termin.werkstattName) &&
        Objects.equals(this.leistung, termin.leistung) &&
        Objects.equals(this.leistungsId, termin.leistungsId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, von, bis, werkstattName, leistung, leistungsId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Termin {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    von: ").append(toIndentedString(von)).append("\n");
    sb.append("    bis: ").append(toIndentedString(bis)).append("\n");
    sb.append("    werkstattName: ").append(toIndentedString(werkstattName)).append("\n");
    sb.append("    leistung: ").append(toIndentedString(leistung)).append("\n");
    sb.append("    leistungsId: ").append(toIndentedString(leistungsId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

