package org.netbeans.minifierbeans.css.validator;

import org.netbeans.minifierbeans.css.options.CssNanoCliOptions;
import org.netbeans.minifierbeans.validators.ValidationResult;

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
