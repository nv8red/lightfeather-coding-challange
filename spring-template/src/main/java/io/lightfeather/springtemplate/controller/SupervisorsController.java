package io.lightfeather.springtemplate.controller;

import io.lightfeather.springtemplate.model.PostSVNotificationRequest;
import io.lightfeather.springtemplate.rest.AWSClient;
import io.lightfeather.springtemplate.supervisors.SubmitManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class SupervisorsController {

    @Autowired
    private AWSClient awsClient;

    @Autowired
    private SubmitManager submitManager;

    @GetMapping(value = "/api/supervisors")
    public Mono<ResponseEntity<?>> getSupervisors() {
        return toServerResponse(awsClient.getSupervisors()).onErrorResume(this::handleErrors);
    }

    @PostMapping(value = "/api/submit",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> postSupervisorNotificationReq(@RequestBody PostSVNotificationRequest requestMono) {
        return toServerResponse(submitManager.postSVNotificationRequest(Mono.just(requestMono))).onErrorResume(this::handleErrors);
    }

    private Mono<ResponseEntity<?>> toServerResponse(Mono<?> ret) {
        return ret.map(body -> new ResponseEntity<>(body, HttpStatus.OK));
    }

    private Mono<ResponseEntity<String>> handleErrors(Throwable t) {
        return Mono.just(new ResponseEntity<>(t.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
