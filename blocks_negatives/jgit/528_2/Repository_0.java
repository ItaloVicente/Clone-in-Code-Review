		try {
			userConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException("User config file "
					+ userConfig.getFile().getAbsolutePath() + " invalid: "
					+ e1);
			e2.initCause(e1);
			throw e2;
		}
