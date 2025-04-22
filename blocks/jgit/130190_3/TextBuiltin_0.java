		Charset charset = UTF_8;
		if (repository != null) {
			String logOutputEncoding = repository.getConfig().getString("i18n"
					null
			if (logOutputEncoding != null) {
				try {
					charset = Charset.forName(logOutputEncoding);
				} catch (IllegalArgumentException e) {
					throw die(CLIText.get().cannotCreateOutputStream);
				}
			}
