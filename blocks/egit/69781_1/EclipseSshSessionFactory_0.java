		JSch jsch = provider.getJSch();
		File home = fs.userHome();
		if (home == null) {
			home = new File(".").getAbsoluteFile(); //$NON-NLS-1$
		}
		File config = new File(new File(home, ".ssh"), //$NON-NLS-1$
				Constants.CONFIG);
		if (config.canRead()) {
			try {
				jsch.setConfigRepository(
						OpenSSHConfig.parseFile(config.getPath()));
			} catch (IOException e) {
				throw new JSchException(e.getLocalizedMessage(), e);
			}
		}
		return jsch;
