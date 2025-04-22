package org.eclipse.jgit.errors;

import java.util.Locale;

public class TranslationStringMissingException extends TranslationBundleException {
	private static final long serialVersionUID = 1L;

	private final String key;

	public TranslationStringMissingException(Class bundleClass
		super("Translation missing for [" + bundleClass.getName() + "
				+ locale.toString() + "
				cause);
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
