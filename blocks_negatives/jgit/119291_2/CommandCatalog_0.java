		final BufferedReader cIn;
		try {
			final InputStream in = cUrl.openStream();
			cIn = new BufferedReader(new InputStreamReader(in, CHARSET));
		} catch (IOException err) {
			return;
		}

		try {
