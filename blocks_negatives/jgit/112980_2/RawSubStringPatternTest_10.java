		try {
			byte[] bytes = text.getBytes("UTF-8");
			return new RawCharSequence(bytes, 0, bytes.length);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
