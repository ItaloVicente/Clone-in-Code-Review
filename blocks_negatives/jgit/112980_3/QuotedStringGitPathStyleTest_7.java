		final byte[] b;
		try {
			b = ('"' + in + '"').getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
