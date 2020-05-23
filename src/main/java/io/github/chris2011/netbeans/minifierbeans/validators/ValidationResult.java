package io.github.chris2011.netbeans.minifierbeans.validators;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.util.Parameters;

/**
 * Validation result. This class can used by miscellaneous validators
 * for collecting errors and warnings.
 * <p>
 * This class is not thread safe.
 * @since 1.75
 */
public final class ValidationResult {

    private final List<Message> errors = new ArrayList<Message>();
    private final List<Message> warnings = new ArrayList<Message>();


    /**
     * Create new validation result.
     */
    public ValidationResult() {
    }

    /**
     * Copy constructor.
     * @param anotherResult result to be copied
     */
    public ValidationResult(ValidationResult anotherResult) {
        merge(anotherResult);
    }

    /**
     * Check whether there are no errors and no warnings present.
     * @return {@code true} if the validation result contains no errors and no warnings
     * @see #hasErrors()
     * @see #hasWarnings()
     */
    public boolean isFaultless() {
        return !hasErrors()
                && !hasWarnings();
    }

    /**
     * Check whether there are some errors present.
     * @return {@code true} if the validation result contains any error
     * @see #isFaultless()
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Get errors.
     * @return list of errors, can be empty but never {@code null}
     */
    public List<Message> getErrors() {
        return new ArrayList<Message>(errors);
    }

    /**
     * Get the first error message or {@code null} if there are no errors.
     * @return the first error messageor {@code null} if there are no errors
     */
    @CheckForNull
    public String getFirstErrorMessage() {
        List<Message> copy = getErrors();
        if (copy.isEmpty()) {
            return null;
        }
        return copy.get(0).getMessage();
    }

    /**
     * Check whether there are some warnings present.
     * @return {@code true} if the validation result contains any warning
     * @see #isFaultless()
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    /**
     * Get warnings.
     * @return list of warnings, can be empty but never {@code null}
     */
    public List<Message> getWarnings() {
        return new ArrayList<Message>(warnings);

    }

    /**
     * Get the first warning message or {@code null} if there are no warnings.
     * @return the first warning message or {@code null} if there are no warnings
     */
    @CheckForNull
    public String getFirstWarningMessage() {
        List<Message> copy = getWarnings();
        if (copy.isEmpty()) {
            return null;
        }
        return copy.get(0).getMessage();
    }

    /**
     * Add error.
     * @param error error to be added
     */
    public void addError(Message error) {
        Parameters.notNull("error", error); // NOI18N
        errors.add(error);
    }

    /**
     * Add warning.
     * @param warning warning to be added
     */
    public void addWarning(Message warning) {
        Parameters.notNull("warning", warning); // NOI18N
        warnings.add(warning);
    }

    /**
     * Merge with some other validation result.
     * <p>
     * This method simply merges all errros and warnings from {@code otherResult} to this one.
     * It can be useful if one validator uses another one for validation.
     * @param otherResult validation result to be merged to this one
     */
    public void merge(ValidationResult otherResult) {
        Parameters.notNull("otherResult", otherResult); // NOI18N
        errors.addAll(otherResult.errors);
        warnings.addAll(otherResult.warnings);
    }

    //~ Inner classes

    /**
     * Validation message.
     */
    public static final class Message {

        private final Object source;
        private final String message;


        /**
         * Create new validation message.
         * @param source source of the message, e.g. "siteRootFolder"
         * @param message message itself, e.g. "Invalid directory specified."
         */
        public Message(Object source, String message) {
            Parameters.notNull("source", source); // NOI18N
            Parameters.notNull("message", message); // NOI18N
            this.source = source;
            this.message = message;
        }

        /**
         * Get source of the message, e.g. "siteRootFolder".
         * @return source of the message, e.g. "siteRootFolder"
         */
        public Object getSource() {
            return source;
        }

        /**
         * Get message itself, e.g. "Invalid directory specified."
         * @return message itself, e.g. "Invalid directory specified.".
         */
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "ValidationMessage{source=" + source + ", message=" + message + '}'; // NOI18N
        }

    }

}