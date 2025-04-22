	/**
	 * Verifies that all translation keys are defined in the root resource bundle.
	 * <p>
	 * This makes sure that all translation bundles will get all strings populated
	 * since the string will be found at last in the root resource bundle.
	 */
	public void testAllTranslationKeysDefinedInRoot() {
		NLS.setLocale(NLS.ROOT_LOCALE);
		for (Class c : translationBundleClasses) {
			NLS.getBundleFor(c);
		}
