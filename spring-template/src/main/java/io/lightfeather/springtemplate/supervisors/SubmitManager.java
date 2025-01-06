package io.lightfeather.springtemplate.supervisors;

import io.lightfeather.springtemplate.model.PostSVNotificationRequest;
import io.lightfeather.springtemplate.model.PostSVNotificationResponse;
import io.lightfeather.springtemplate.util.validation.SubmitRequestValidator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SubmitManager {

    /**
     *   - Validate that the data does not need to be rejected
     *   - Print data to console (in prod crud would probably be performed here)
     *   - Add warning if no contact method is provided
     */
    public Mono<PostSVNotificationResponse> postSVNotificationRequest(Mono<PostSVNotificationRequest> request) {
        return request
                .flatMap(SubmitRequestValidator::submitRequestValidationReject)
                .map(this::printToConsole)
                .map(SubmitRequestValidator::submitRequestValidationWarn);
    }

    private PostSVNotificationRequest printToConsole(PostSVNotificationRequest request) {
        System.out.println(request.toString());
        return request;
    }
}
