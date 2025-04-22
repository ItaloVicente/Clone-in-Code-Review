			throws UnsupportedEncodingException {
		try {
			Class.forName("java.text.Normalizer");
		} catch (ClassNotFoundException e) {
			return;
		}

