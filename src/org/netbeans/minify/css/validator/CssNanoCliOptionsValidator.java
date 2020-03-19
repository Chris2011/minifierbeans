package org.netbeans.minify.css.validator;

import org.netbeans.minify.css.options.CssNanoCliOptions;
import org.netbeans.minify.validators.ValidationResult;

public class CssNanoCliOptionsValidator {

    private final ValidationResult result = new ValidationResult();

    public CssNanoCliOptionsValidator validateCssNanoCli() {
        return CssNanoCliOptionsValidator.this.validateCssNanoCli(CssNanoCliOptions.getInstance().getCssNanoCli());
    }

    public CssNanoCliOptionsValidator validateCssNanoCli(String cssNanoCli) {
        CssNanoValidationUtils.validateCssNanoCli(result, cssNanoCli);

        return this;
    }

    public ValidationResult getResult() {
        return result;
    }
}
