package com.example.demo.api;

import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Auto-generated stub by org.openapitools:openapi-generator-maven-plugin:5.1.0
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-01T18:01:02.009225+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.terminbuchung.base-path:}")
public class WerkstattApiController implements WerkstattApi {

    private final WerkstattApiDelegate delegate;

    public WerkstattApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) WerkstattApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new WerkstattApiDelegate() {});
    }

    @Override
    public WerkstattApiDelegate getDelegate() {
        return delegate;
    }

}
