		String w = readPipe(userHome()
				new String[] { "bash"
				Charset.defaultCharset().name());
		if (w != null)
			return new File(w).getParentFile().getParentFile();
		return null;
