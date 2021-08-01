package com.example.demo.api;

import com.example.demo.api.model.Termin;
import com.example.demo.api.model.TerminRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Auto-generated stub by org.openapitools:openapi-generator-maven-plugin:5.1.0
 * A delegate to be called by the {@link WerkstattApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-01T18:01:02.009225+02:00[Europe/Berlin]")
public interface WerkstattApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /werkstatt/{werkstattId}/termin/{terminId} : Termin abrufen
     *
     * @param werkstattId Die Kennung der Werkstatt (required)
     * @param terminId Die Id des Termines (required)
     * @return Success (status code 200)
     *         or Not Found (status code 404)
     * @see WerkstattApi#getTermin
     */
    default ResponseEntity<Termin> getTermin(String werkstattId,
        String terminId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"werkstattName\" : \"werkstattName\", \"leistung\" : \"leistung\", \"von\" : \"von\", \"leistungsId\" : \"leistungsId\", \"id\" : \"id\", \"bis\" : \"bis\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /werkstatt/{werkstattId}/termine : Eine Liste aller Termine mit der Werkstatt
     *
     * @param werkstattId Die Kennung der Werkstatt (required)
     * @param von Nur Termine nach diesem Datum (optional)
     * @param bis Nur Termine vor diesem Datum (optional)
     * @param leistungsId Nur Termine dieser Leistung (optional)
     * @return Eine Terminliste (status code 200)
     * @see WerkstattApi#getTermine
     */
    default ResponseEntity<List<Termin>> getTermine(String werkstattId,
        String von,
        String bis,
        String leistungsId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"werkstattName\" : \"werkstattName\", \"leistung\" : \"leistung\", \"von\" : \"von\", \"leistungsId\" : \"leistungsId\", \"id\" : \"id\", \"bis\" : \"bis\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /werkstatt/{werkstattId}/terminvorschlag/ : Terminvorschläge erhalten
     *
     * @param werkstattId Die Kennung der Werkstatt (required)
     * @param leistungsId Terminvorschläge für diese Leistung geben (required)
     * @param von Mögliche Termine nach diesem Datum (optional)
     * @param bis Mögliche Termine vor diesem Datum (optional)
     * @return Eine Liste an möglichen Terminen (status code 200)
     * @see WerkstattApi#getTerminvorschlaege
     */
    default ResponseEntity<List<Termin>> getTerminvorschlaege(String werkstattId,
        String leistungsId,
        String von,
        String bis) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"werkstattName\" : \"werkstattName\", \"leistung\" : \"leistung\", \"von\" : \"von\", \"leistungsId\" : \"leistungsId\", \"id\" : \"id\", \"bis\" : \"bis\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /werkstatt/{werkstattId}/termin : Neuen Termin erstellen
     *
     * @param werkstattId Die Kennung der Werkstatt (required)
     * @param termin Der neue Termin (required)
     * @return Success (status code 200)
     *         or Conflict (status code 409)
     * @see WerkstattApi#postTermin
     */
    default ResponseEntity<Termin> postTermin(String werkstattId,
        TerminRequest termin) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"werkstattName\" : \"werkstattName\", \"leistung\" : \"leistung\", \"von\" : \"von\", \"leistungsId\" : \"leistungsId\", \"id\" : \"id\", \"bis\" : \"bis\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
