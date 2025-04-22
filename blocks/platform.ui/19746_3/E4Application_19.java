		String locale = Locale.getDefault().toString();
		appContext.set(TranslationService.LOCALE, locale);
		TranslationService bundleTranslationProvider = TranslationProviderFactory
				.bundleTranslationService(appContext);
		appContext.set(TranslationService.class, bundleTranslationProvider);

