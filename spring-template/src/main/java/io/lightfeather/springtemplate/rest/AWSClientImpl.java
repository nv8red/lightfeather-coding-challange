package io.lightfeather.springtemplate.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lightfeather.springtemplate.model.Supervisor;
import io.lightfeather.springtemplate.util.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AWSClientImpl implements AWSClient {

    private static final String GET_SUPERVISORS_API_SUFFIX = "api/managers";
    private static final String FORMAT_SUPERVISORS = "%s - %s, %s";
    private static final String INCORRECT_URL_ERROR = "Supervisors were not formatted correctly at source; " +
            "Check managers url in application.properties or ensure managers endpoint is functioning. Original error:\n";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${spring.application.managers.url}")
    private String supervisorSourceURL;

    /**
     * Performs the following steps:
     *      - gets a list of supervisors from the source endpoint
     *      - removes supervisors with numeric jurisdictions
     *      - maps to the format "jurisdiction - lastName, firstName"
     *      - sorts alphabetically
     *      - maps to the correct response object and returns
     */
    public Mono<List<String>> getSupervisors() {
        return getSupervisorsFromAWS()
                .flatMapMany(Flux::fromIterable)
                .filter(supervisor -> CommonUtils.isNonNumeric(supervisor.getJurisdiction()))
                .map(supervisor -> String.format(FORMAT_SUPERVISORS,
                        supervisor.getJurisdiction(),
                        supervisor.getLastName(),
                        supervisor.getFirstName()))
                .collect(Collectors.toList())
                .map(list -> {
                    List<String> sortedList = new ArrayList<>(list);
                    Collections.sort(sortedList);
                    return sortedList;
                });
    }

    private Mono<List<Supervisor>> getSupervisorsFromAWS() {
        return Mono.fromFuture(client
                        .sendAsync(getSupervisorRequest(), HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body))
                .flatMap(jsonString -> {
                    try {
                        return Mono.just(mapper.readValue(jsonString, new TypeReference<List<Supervisor>>(){}));
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException(INCORRECT_URL_ERROR + e.getMessage()));
                    }
                });
    }

    private HttpRequest getSupervisorRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(supervisorSourceURL + GET_SUPERVISORS_API_SUFFIX))
                .build();
    }
}
