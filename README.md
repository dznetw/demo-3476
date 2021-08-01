# demo#3476

## Notes

### **API implementation**

I used the Open API generator plugin to create initial stubs for this POC:

```xml
<!-- maven dependency: OpenAPI generator -->
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>${openapi-generator-maven-plugin.version}</version>
</plugin>
```

The controller logic I implemented can be found in `com.example.demo.api.WerkstattApiDelegateImpl`.
It does not cover all use cases and error handling because the initial task description advised to prioritize business logic.
Admittedly, I did not verify whether the stubs are correctly generated or not, but it seemed fine at first glance :)

### **Main business logic**

I considered `com.example.demo.services.AppointmentProposalService` as the central business logic.
Therefore, I implemented this one first and used a partial integration test to support me with iteratively enhancing the logic.
It focuses on creating proposals for a certain day, because I assume that scheduling appointments over day transitions is usually not possible (considering business hours, etc.).
Unfortunately, the API just accepts generic strings as time boundaries, so I took an educated guess.
(Adding a format for these strings within the OAS might have been useful but not mandatory.)
It doesn't cover all edge cases, yet.

### **CSV import**

Due to the time constraint, I did not implement the CSV import because I rated it rather low in priority.
Additionally, I used NoSQL databases far more often than SQL databases, which would have required me to read up on the specific dialects again.
First, I would need a refresher course on that topic.
Anyway, I would have used an easy-to-use library like https://www.univocity.com to parse the files and build an init-script or utility class.
Afaik, it would be optimal to use Flyway in order to correctly version the database.

```xml
<!-- maven dependency: univocity -->
    <dependency>
        <groupId>com.univocity</groupId>
        <artifactId>univocity-parsers</artifactId>
        <version>2.9.1</version>
        <type>jar</type>
    </dependency>
```

But the proposal algorithm was far more interesting to me than this.

### **General improvement**

Flyway would probably be mandatory if this wasn't a POC.
It eliminates Hibernate implementation-specifics and allows for better versioning capabilities, afaik.
But I never used it before, and it would take too much time for me to get familiar with it.

### **AppointmentRepository**

It might be useful to swap the lookup method to a streaming procedure, depending on the amount of data.
