	/**
	 * Initializes the given context with the locale and the TranslationService
	 * to use.
	 *
	 * @param appContext
	 *            The application context to which the locale and the
	 *            TranslationService should be set.
	 */
	private static void initializeLocalization(IEclipseContext appContext) {
		appContext.set(TranslationService.LOCALE, Locale.getDefault());
		appContext.set(TranslationService.class, TranslationProviderFactory.bundleTranslationService(appContext));
	}

