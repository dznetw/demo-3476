package com.example.demo.api;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Auto-generated stub by org.openapitools:openapi-generator-maven-plugin:5.1.0
 */
public class ApiUtil {

    private ApiUtil() {
    }

    public static void setExampleResponse(NativeWebRequest req, String contentType, String example) {
        try {
            HttpServletResponse res = req.getNativeResponse(HttpServletResponse.class);
            res.setCharacterEncoding("UTF-8");
            res.addHeader("Content-Type", contentType);
            res.getWriter().print(example);
        } catch (NullPointerException | IOException e) {
            throw new IllegalStateException("Unable to set example response", e);
        }
    }
}
