		Charset cs;
		try {
			cs = parseEncoding(raw);
		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			cs = UTF_8;
		}

