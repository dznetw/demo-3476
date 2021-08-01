package com.example.demo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/**
 * Auto-generated stub by org.openapitools:openapi-generator-maven-plugin:5.1.0
 * TerminRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-01T18:01:02.009225+02:00[Europe/Berlin]")
public class TerminRequest   {
  @JsonProperty("von")
  private String von;

  @JsonProperty("bis")
  private String bis;

  @JsonProperty("leistungsId")
  private String leistungsId;

  public TerminRequest von(String von) {
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

  public TerminRequest bis(String bis) {
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

  public TerminRequest leistungsId(String leistungsId) {
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
    TerminRequest terminRequest = (TerminRequest) o;
    return Objects.equals(this.von, terminRequest.von) &&
        Objects.equals(this.bis, terminRequest.bis) &&
        Objects.equals(this.leistungsId, terminRequest.leistungsId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(von, bis, leistungsId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TerminRequest {\n");
    
    sb.append("    von: ").append(toIndentedString(von)).append("\n");
    sb.append("    bis: ").append(toIndentedString(bis)).append("\n");
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

