
package org.eclipse.jgit.nls;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;

public abstract class TranslationBundle {

	private Locale effectiveLocale;
	private ResourceBundle resourceBundle;

	public Locale effectiveLocale() {
		return effectiveLocale;
	}

	public ResourceBundle resourceBundle() {
		return resourceBundle;
	}

	void load(Locale locale)
			throws TranslationBundleLoadingException {
		Class bundleClass = getClass();
		try {
			resourceBundle = ResourceBundle.getBundle(bundleClass.getName()
					locale
		} catch (MissingResourceException e) {
			throw new TranslationBundleLoadingException(bundleClass
		}
		this.effectiveLocale = resourceBundle.getLocale();

		for (Field field : bundleClass.getFields()) {
			if (field.getType().equals(String.class)) {
				try {
					String translatedText = resourceBundle.getString(field.getName());
					field.set(this
				} catch (MissingResourceException e) {
					throw new TranslationStringMissingException(bundleClass
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new Error(e);
				}
			}
		}
	}
}
