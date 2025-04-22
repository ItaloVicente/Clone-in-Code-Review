	private void readAWSKeys() throws IOException
		FileBasedConfig c = new FileBasedConfig(new File(credentialsPath)
				FS.DETECTED);
		c.load();
		accessKey = c.getString("default"
		secretKey = c.getString("default"
		if (accessKey == null || accessKey.isEmpty()) {
			throw new IllegalStateException(
					"No accessKey in " + credentialsPath);
		}
		if (secretKey == null || secretKey.isEmpty()) {
			throw new IllegalStateException(
					"No secretKey in " + credentialsPath);
		}
	}

