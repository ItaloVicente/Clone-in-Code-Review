
	public File gitPrefix() {
		String osName = SystemReader.getInstance().getProperty("os.name");
		if (osName.startsWith("Windows") || osName.startsWith("Mac")) {
			String w = readPipe(userHome
					"-c"
					"which git" }
					Charset.defaultCharset().name());
			File f = new File(w);
			return f.getParentFile().getParentFile();
		}
		return null;
	}
