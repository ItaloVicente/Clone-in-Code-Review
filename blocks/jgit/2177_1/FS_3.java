
	public File gitPrefix() throws IOException {
		String w;
		w = readPipe(null
				Charset.defaultCharset().name());
		if (w == null) {
			w = readPipe(null
					Charset.defaultCharset().name());
		}
		if (w == null) {
			w = readPipe(userHome
					"which git" }
					Charset.defaultCharset().name());
		}
		if (w == null)
			return null;
		File f = new File(w);
		return f.getParentFile().getParentFile();
	}
