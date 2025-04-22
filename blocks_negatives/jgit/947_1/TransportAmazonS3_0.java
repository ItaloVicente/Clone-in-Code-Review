		Properties props = null;
		File propsFile = new File(local.getDirectory(), uri.getUser());
		if (!propsFile.isFile())
			propsFile = new File(local.getFS().userHome(), uri.getUser());
		if (propsFile.isFile()) {
			try {
				props = AmazonS3.properties(propsFile);
			} catch (IOException e) {
				throw new NotSupportedException(MessageFormat.format(JGitText.get().cannotReadFile, propsFile), e);
			}
		} else {
			props = new Properties();
			props.setProperty("accesskey", uri.getUser());
			props.setProperty("secretkey", uri.getPass());
		}

		s3 = new AmazonS3(props);
