package org.eclipse.core.databinding.old_build;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.validation.IValidator;

public interface IStrategyDefaultsProvider {
	public StrategyDefaults getDefaults(Object sourceType, Object destinationType);
}
