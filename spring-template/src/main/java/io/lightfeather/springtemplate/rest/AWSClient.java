package io.lightfeather.springtemplate.rest;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface for test implementations
 */
public interface AWSClient {

    Mono<List<String>> getSupervisors();
}
