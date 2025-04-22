	private StoredConfig loadUserConfig() throws IOException {
		FileBasedConfig c = SystemReader.getInstance().openUserConfig(null,
				FS.DETECTED);
		try {
			c.load();
		} catch (ConfigInvalidException e1) {
			throw new IOException(MessageFormat
					.format(LfsText.get().userConfigInvalid, c.getFile()
							.getAbsolutePath(), e1),
					e1);
		}

		return c;
