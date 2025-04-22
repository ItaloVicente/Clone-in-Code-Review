	private JSch getDefaultJSch() throws JSchException {
		if (defaultJSch == null) {
			defaultJSch = createDefaultJSch();
			for (Object name : defaultJSch.getIdentityNames()) {
				byIdentityFile.put((String) name, defaultJSch);
			}
		}
		return defaultJSch;
	}

