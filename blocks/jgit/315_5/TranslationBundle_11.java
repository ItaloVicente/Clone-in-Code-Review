
package org.eclipse.jgit.nls;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;

public abstract class TranslationBundle {

	private Locale effectiveLocale;

	public Locale getEffectiveLocale() {
		return effectiveLocale;
	}

	void load(Locale locale) throws TranslationBundleLoadingException {
		Class bundleClass = getClass();
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle(bundleClass.getName()
		} catch (MissingResourceException e) {
			throw new TranslationBundleLoadingException(bundleClass
		}
		this.effectiveLocale = bundle.getLocale();

		for (Field field : bundleClass.getFields()) {
			if (field.getType().equals(String.class)) {
				try {
					String translatedText = bundle.getString(field.getName());
					field.set(this
				} catch (MissingResourceException e) {
					throw new TranslationStringMissingException(bundleClass
				} catch (IllegalArgumentException e) {
					throw new Error(e);
				} catch (IllegalAccessException e) {
					throw new Error(e);
				}
			}
		}
	}
}
