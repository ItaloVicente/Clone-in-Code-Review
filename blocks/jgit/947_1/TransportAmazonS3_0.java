	private Properties loadProperties() throws NotSupportedException {
		if (local.getDirectory() != null) {
			File propsFile = new File(local.getDirectory()
			if (propsFile.isFile())
				return loadPropertiesFile(propsFile);
		}

		File propsFile = new File(local.getFS().userHome()
		if (propsFile.isFile())
			return loadPropertiesFile(propsFile);

		Properties props = new Properties();
		props.setProperty("accesskey"
		props.setProperty("secretkey"
		return props;
	}

	private static Properties loadPropertiesFile(File propsFile)
			throws NotSupportedException {
		try {
			return AmazonS3.properties(propsFile);
		} catch (IOException e) {
			throw new NotSupportedException(MessageFormat.format(
					JGitText.get().cannotReadFile
		}
	}

