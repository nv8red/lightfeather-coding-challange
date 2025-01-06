package io.lightfeather.springtemplate.util.validation;

import io.lightfeather.springtemplate.model.PostSVNotificationRequest;
import io.lightfeather.springtemplate.model.PostSVNotificationResponse;
import reactor.core.publisher.Mono;

public class SubmitRequestValidator {

    private static final String ERRORS = "The following errors are present:\n";
    private static final String FIRST_NAME_NOT_PROVIDED = "  - First name is a required field\n";
    private static final String LAST_NAME_NOT_PROVIDED = "  - Last name is a required field\n";
    private static final String SUPERVISOR_NOT_PROVIDED = "  - Supervisor is a required field\n";
    private static final String NO_CONTACT_METHOD_WARNING = "Submission accepted, but no method of contact was provided\n";

    /** Validate for anything that we actually want to be a rejection. */
    public static Mono<PostSVNotificationRequest> submitRequestValidationReject(PostSVNotificationRequest request) {
        StringBuilder errors = new StringBuilder(ERRORS);
        boolean errorsPresent = false;
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            errorsPresent = true;
            errors.append(FIRST_NAME_NOT_PROVIDED);
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()){
            errorsPresent = true;
            errors.append(LAST_NAME_NOT_PROVIDED);
        }
        if (request.getSupervisor() == null || request.getSupervisor().charAt(0) == 160) {
            errorsPresent = true;
            errors.append(SUPERVISOR_NOT_PROVIDED);
        }
        if (errorsPresent) {
            return Mono.error(new IllegalArgumentException(errors.toString()));
        } else {
            return Mono.just(request);
        }
    }

    /** I thought it made sense to let people know that they didn't have
     * a method of contact, even if it was accepted.
     * */
    public static PostSVNotificationResponse submitRequestValidationWarn(PostSVNotificationRequest request) {
        if (request.getPhone() == null || request.getPhone().isEmpty() &&
                request.getEmail() == null || request.getEmail().isEmpty()) {
            return new PostSVNotificationResponse(NO_CONTACT_METHOD_WARNING);
        } else {
            return new PostSVNotificationResponse();
        }
    }
}
