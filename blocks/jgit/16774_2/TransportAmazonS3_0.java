		String user = uri.getUser();
		String pass = uri.getPass();
		if (user != null && pass != null) {
		        props.setProperty("accesskey"
		        props.setProperty("secretkey"
		} else
			throw new NotSupportedException(MessageFormat.format(
					JGitText.get().cannotReadFile
