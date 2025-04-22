package org.eclipse.core.databinding.old_build;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.validation.IValidator;

public class StrategyDefaults {
	private IConverter converter;
	private IValidator validator;

	public StrategyDefaults() {
	}

	public StrategyDefaults(IConverter converter, IValidator validator) {
		this.setConverter(converter);
		this.setValidator(validator);
	}

	public void setConverter(IConverter converter) {
		this.converter = converter;
	}

	public IConverter getConverter() {
		return converter;
	}

	public void setValidator(IValidator validator) {
		this.validator = validator;
	}

	public IValidator getValidator() {
		return validator;
	}
}
