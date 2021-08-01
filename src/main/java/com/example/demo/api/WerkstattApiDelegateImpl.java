package com.example.demo.api;

import com.example.demo.api.model.Termin;
import com.example.demo.api.model.TerminRequest;
import com.example.demo.model.Appointment;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.services.AppointmentProposalService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 */
public class WerkstattApiDelegateImpl implements WerkstattApiDelegate {

    private final AppointmentRepository repository;
    private final AppointmentProposalService service;

    public WerkstattApiDelegateImpl(AppointmentRepository repository, AppointmentProposalService service) {
        this.repository = repository;
        this.service = service;
    }

    @SuppressWarnings("java:S1172")
    public static Termin mapApiModel(Appointment appointment) {
        // TODO: implement mapping between models
        return new Termin();
    }

    @SuppressWarnings("java:S1172")
    public static Appointment mapApiModel(TerminRequest terminRequest) {
        // TODO: implement mapping between models
        return new Appointment();
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
    @Override
    public ResponseEntity<Termin> getTermin(String werkstattId,
                                            String terminId) {

        // TODO: parsing error must be handled with either 500 or 404
        final long id = Long.parseLong(terminId);

        final Optional<Appointment> appointment = repository.findAppointmentByIdAndWorkshop(id, werkstattId);

        if (appointment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(mapApiModel(appointment.get()));

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
    @Override
    public ResponseEntity<List<Termin>> getTermine(String werkstattId,
                                                   String von,
                                                   String bis,
                                                   String leistungsId) {

        // TODO (optional): if input parameters are invalid, handle errors, otherwise user might get an empty result

        // TODO: if 'von', 'bis' and/or 'leistungsId' is available, parse and set filter parameters correctly:
        final LocalDate localDate = LocalDate.MIN;
        final LocalTime from = LocalTime.MIN;
        final LocalTime until = LocalTime.MAX;

        final List<Termin> collect = repository.findByWorkshopAndDateRange(werkstattId, localDate, from, until)
                .stream()
                .map(WerkstattApiDelegateImpl::mapApiModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(collect);

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
    @Override
    public ResponseEntity<List<Termin>> getTerminvorschlaege(String werkstattId,
                                                             String leistungsId,
                                                             String von,
                                                             String bis) {

        // TODO: if 'von' and/or 'bis' is available, parse and set filter parameters correctly:
        final LocalDate localDate = LocalDate.MIN;
        final LocalTime from = LocalTime.MIN;
        final LocalTime until = LocalTime.MAX;

        // TODO: if 'von' and 'bis' covers a span over multiple days, call service multiple times and  merge results
        final List<Termin> proposals = service.getProposalsFor(werkstattId, localDate, leistungsId, from, until)
                .stream()
                .map(WerkstattApiDelegateImpl::mapApiModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(proposals);
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
    @Override
    public ResponseEntity<Termin> postTermin(String werkstattId,
                                             TerminRequest termin) {

        // TODO: implement possible error handling
        repository.save(mapApiModel(termin));

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
