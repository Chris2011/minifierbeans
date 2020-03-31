package org.netbeans.minifierbeans.css.validator;

import org.netbeans.minifierbeans.validators.ExternalExecutableValidator;
import org.netbeans.minifierbeans.validators.ValidationResult;
import org.openide.util.NbBundle;

public final class CssNanoValidationUtils {

    public static final String CSS_NANO_CLI_PATH = "cssnano-cli.path"; // NOI18N

    private CssNanoValidationUtils() {
    }

    @NbBundle.Messages("ValidationUtils.cssNanoCli.name=CSSNano CLI")
    public static void validateCssNanoCli(ValidationResult result, String cssNanoCli) {
        String warning = ExternalExecutableValidator.validateCommand(cssNanoCli, "Bundle.ValidationUtils_ngcli_name()");

        if (warning != null) {
            result.addWarning(new ValidationResult.Message(CSS_NANO_CLI_PATH, warning));
        }
    }
}