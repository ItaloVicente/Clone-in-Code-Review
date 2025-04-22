		return acceptsGzipEncoding(req.getHeader(HDR_ACCEPT_ENCODING));
	}

	static boolean acceptsGzipEncoding(String accepts) {
		if (accepts == null)
			return false;

		int b = 0;
		while (b < accepts.length()) {
			int comma = accepts.indexOf('
			int e = 0 <= comma ? comma : accepts.length();
			String term = accepts.substring(b
			if (term.equals(ENCODING_GZIP))
				return true;
			b = e + 1;
		}
		return false;
