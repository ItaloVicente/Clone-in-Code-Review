	public Locale toLocale(String str) {
		return toLocale(str, Locale.getDefault());
	}

	private Locale toLocale(String localeString, Locale defaultLocale) {
		if (localeString == null) {
			if (logService != null)
				logService.log(LogService.LOG_ERROR,
						"Given locale String is null" + " - Default Locale will be used instead."); //$NON-NLS-1$//$NON-NLS-2$
			return defaultLocale;
		}

		String language = ""; //$NON-NLS-1$
		String country = ""; //$NON-NLS-1$
		String variant = ""; //$NON-NLS-1$

		String[] localeParts = localeString.split("_"); //$NON-NLS-1$
		if (localeParts.length == 0 || localeParts.length > 3
				|| (localeParts.length == 1 && localeParts[0].length() == 0)) {
			logInvalidFormat(localeString, logService);
			return defaultLocale;
		}

		if (localeParts[0].length() > 0 && !localeParts[0].matches("[a-zA-Z]{2,8}")) { //$NON-NLS-1$
			logInvalidFormat(localeString, logService);
			return defaultLocale;
		}

		language = localeParts[0];

		if (localeParts.length > 1) {
			if (localeParts[1].length() > 0 && !localeParts[1].matches("[a-zA-Z]{2}|[0-9]{3}")) { //$NON-NLS-1$
				if (language.length() > 0) {
					if (logService != null)
						logService.log(LogService.LOG_ERROR, "Invalid locale format: " + localeString //$NON-NLS-1$
								+ " - Only language part will be used to create the Locale."); //$NON-NLS-1$
					return new Locale(language);
				}
				logInvalidFormat(localeString, logService);
				return defaultLocale;
			}

			country = localeParts[1];
		}

		if (localeParts.length == 3) {
			if (localeParts[2].length() == 0) {
				if (logService != null)
					logService.log(LogService.LOG_ERROR, "Invalid locale format: " + localeString //$NON-NLS-1$
							+ " - Only language and country part will be used to create the Locale."); //$NON-NLS-1$
				return new Locale(language, country);
			}
			variant = localeParts[2];
		}

		return new Locale(language, country, variant);
	}

	private static HashSet<String> invalidLocalesLogged = new HashSet<>();

	static void logInvalidFormat(String str, LogService logService) {
		if (logService != null && !invalidLocalesLogged.contains(str)) {
			invalidLocalesLogged.add(str);
			logService.log(LogService.LOG_ERROR, "Invalid locale format: " + str //$NON-NLS-1$
					+ " - Default Locale will be used instead."); //$NON-NLS-1$
		}
	}
