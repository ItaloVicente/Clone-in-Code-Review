	public static String urlEncode(String url
		String encoded;
		try {
			encoded = URLEncoder.encode(url
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(JGitText.get().couldNotURLEncodeToUTF8
		}
		if (keepPathSlash) {
			encoded = encoded.replace("%2F"
		}
		return encoded;
	}

