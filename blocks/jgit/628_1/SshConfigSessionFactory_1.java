	protected JSch getJSch(final OpenSshConfig.Host hc
		if (defaultJSch == null) {
			defaultJSch = createDefaultJSch(fs);
			for (Object name : defaultJSch.getIdentityNames()) {
				byIdentityFile.put((String) name
			}
		}

