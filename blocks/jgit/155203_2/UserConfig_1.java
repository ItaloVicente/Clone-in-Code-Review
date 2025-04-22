	private static String getCommitTemplatePathInternal(Config rc
		String templatePath = system().getenv(envKey);

		if (templatePath == null) {
			templatePath = rc.getString("commit"
		}

		return templatePath;
	}

