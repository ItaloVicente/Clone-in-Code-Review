		Charset outputEncoding = UTF_8;
		if (repository != null) {
			String encoding = repository.getConfig().getString("i18n"
			if (encoding != null) {
				try {
					outputEncoding = Charset.forName(encoding);
				} catch (IllegalArgumentException e) {
					throw die(CLIText.get().cannotCreateOutputStream);
				}
			}
