	public static String urlEncode(String url
		String encoded;
		try {
			encoded = URLEncoder.encode(url
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported."
		}
		if (keepPathSlash) {
			encoded = encoded.replace("%2F"
		}
		return encoded;
	}

