package org.eclipse.jgit.errors;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationBundleLoadingException extends TranslationBundleException {
	private static final long serialVersionUID = 1L;

	public TranslationBundleLoadingException(Class bundleClass
		super("Loading of translation bundle failed for ["
				+ bundleClass.getName() + "
				bundleClass
	}
}
