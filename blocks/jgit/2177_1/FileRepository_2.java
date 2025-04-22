	private void loadSysConfig() throws IOException {
		try {
			sysConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(MessageFormat.format(JGitText
					.get().sysConfigFileInvalid
					.getAbsolutePath()
			e2.initCause(e1);
			throw e2;
		}
	}

