	private void loadSystemConfig() throws IOException {
		try {
			systemConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(MessageFormat.format(JGitText
					.get().systemConfigFileInvalid
					.getAbsolutePath()
			e2.initCause(e1);
			throw e2;
		}
	}

