		String enc = parseEncodingName(b);
		if (enc == null) {
			return UTF_8;
		}

		String name = enc.trim();
