		String defaultLocaleString = Locale.getDefault().toString();

		Locale transformedLocale = ResourceBundleHelper.toLocale(
				defaultLocaleString, Locale.ENGLISH);

		appContext.set(TranslationService.LOCALE, transformedLocale.toString());
