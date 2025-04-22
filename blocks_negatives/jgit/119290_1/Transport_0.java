		BufferedReader br;
		try {
			InputStream urlIn = url.openStream();
			br = new BufferedReader(new InputStreamReader(urlIn, CHARSET));
		} catch (IOException err) {
			return;
		}

		try {
