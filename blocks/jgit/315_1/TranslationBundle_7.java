
package org.eclipse.jgit.nls;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

abstract public class TranslationBundle {

	private Locale effectiveLocale;

	public Locale getEffectiveLocale() {
		return effectiveLocale;
	}

	void load(Locale locale) {
		Class bundleClass = getClass();
		ResourceBundle bundle = ResourceBundle.getBundle(bundleClass.getName()
		this.effectiveLocale = bundle.getLocale();

		Field[] translatableFields = bundleClass.getFields();
		for (Field field : translatableFields) {
			if (field.getType().equals(String.class)) {
				String translatedText = bundle.getString(field.getName());
				try {
					field.set(this
				} catch (IllegalArgumentException e) {
					throw new Error(e);
				} catch (IllegalAccessException e) {
					throw new Error(e);
				}
			}
		}
	}
}
