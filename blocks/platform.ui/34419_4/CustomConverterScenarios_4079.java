package org.eclipse.jface.tests.databinding.scenarios;

import java.lang.reflect.Method;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public class CustomBeanUpdateValueStrategy extends UpdateValueStrategy {

	@Override
	public IConverter createConverter(Object fromType, Object toType) {
		if (fromType instanceof CustomBeanModelType) {
			CustomBeanModelType customBeanModelType = (CustomBeanModelType) fromType;
			fromType = customBeanModelType.getType();
		}
		if (toType instanceof CustomBeanModelType) {
			CustomBeanModelType customBeanModelType = (CustomBeanModelType) toType;
			toType = customBeanModelType.getType();
		}
		return super.createConverter(fromType, toType);
	}

	@Override
	protected void fillDefaults(IObservableValue source,
			IObservableValue destination) {
		if (destination.getValueType() instanceof CustomBeanModelType) {
			if (beforeSetValidator==null) {
				CustomBeanModelType property = (CustomBeanModelType) destination.getValueType();
				String propertyName = property.getPropertyName();
				String getValidatorMethodName = "get" + upperCaseFirstLetter(propertyName) + "Validator"; //$NON-NLS-1$ //$NON-NLS-2$

				Class objectClass = property.getObject().getClass();

				Method getValidator;
				try {
					getValidator = objectClass.getMethod(getValidatorMethodName,
							new Class[] { Class.class });
					beforeSetValidator= (IValidator) getValidator.invoke(property
								.getObject(), new Object[0]);
				} catch (Exception e) {
				}

			}
 		}
		super.fillDefaults(source, destination);
	}

	private String upperCaseFirstLetter(String name) {
		String result = name.substring(0, 1).toUpperCase() + name.substring(1);
		return result;
	}

	@Override
	public Boolean isAssignableFromTo(Object fromType, Object toType) {
		if (fromType instanceof CustomBeanModelType) {
			fromType = ((CustomBeanModelType) fromType).getType();
		}
		if (toType instanceof CustomBeanModelType) {
			toType = ((CustomBeanModelType) toType).getType();
		}
		return super.isAssignableFromTo(fromType, toType);
	}
}
