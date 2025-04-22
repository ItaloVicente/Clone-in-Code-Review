package org.eclipse.jgit.errors;

import java.util.Locale;

public abstract class TranslationBundleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final Class bundleClass;
	private final Locale locale;

	protected TranslationBundleException(String message
		super(message
		this.bundleClass = bundleClass;
		this.locale = locale;
	}

	final public Class getBundleClass() {
		return bundleClass;
	}

	final public Locale getLocale() {
		return locale;
	}
}
