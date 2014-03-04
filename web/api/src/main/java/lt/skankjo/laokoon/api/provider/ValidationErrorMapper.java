package lt.skankjo.laokoon.api.provider;

import jersey.repackaged.com.google.common.base.Preconditions;
import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by skankjo on 3/4/14.
 */
@Provider
public class ValidationErrorMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        Preconditions.checkArgument(exception instanceof ConstraintViolationException);
        ConstraintViolationException exceptions = (ConstraintViolationException) exception;

        List<ValidationError> errors = Lists.newArrayList();
        Set<ConstraintViolation<?>> violations = exceptions.getConstraintViolations();
        for(ConstraintViolation<?> violation : violations) {
            errors.add(new ValidationError(violation.getMessage(), violation.getMessageTemplate(), violation.getPropertyPath(), violation.getInvalidValue()));
        }

        Map<String, List<ValidationError>> msg = Maps.newHashMap();
        msg.put("errors", errors);
        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }

    public static final class ValidationError{
        private final String message;
        private final String messageTemplate;
        private final String path;
        private final String invalidValue;

        public ValidationError(String message, String messageTemplate, Path path, Object invalidValue) {
            this.message = message;
            this.messageTemplate = messageTemplate;
            this.path = path == null ? null : path.toString();
            this.invalidValue = invalidValue == null ? null : invalidValue.toString();
        }

        public String getMessage() {
            return message;
        }

        public String getMessageTemplate() {
            return messageTemplate;
        }

        public String getPath() {
            return path;
        }

        public String getInvalidValue() {
            return invalidValue;
        }
    }
}
