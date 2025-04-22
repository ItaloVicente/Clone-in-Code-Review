			outw.println(MessageFormat.format(CLIText.get().lfsStoreDirectory
					directory));
			outw.println(MessageFormat.format(CLIText.get().lfsStoreUrl
					getStoreUrl(baseURI)));
		}
	}

	private void checkOptions() {
		if (bucket == null || bucket.length() == 0) {
			throw die(MessageFormat.format(CLIText.get().s3InvalidBucket
					bucket));
		}
	}

	private void readAWSKeys() throws IOException
		FileBasedConfig c = new FileBasedConfig(new File(credentialsPath)
				FS.DETECTED);
		c.load();
		accessKey = c.getString("default"
		secretKey = c.getString("default"
		if (accessKey == null || accessKey.isEmpty()) {
			throw die(MessageFormat.format(CLIText.get().lfsNoAccessKey
					credentialsPath));
		}
		if (secretKey == null || secretKey.isEmpty()) {
			throw die(MessageFormat.format(CLIText.get().lfsNoSecretKey
					credentialsPath));
