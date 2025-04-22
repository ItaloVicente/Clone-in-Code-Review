
		String raw = myBundle.getString(key);

		if (keepAmpersands || raw.indexOf(AMPERSAND) < 0)
			return raw;

		StringBuilder sb = new StringBuilder(raw.length());
		for (int i = 0; i < raw.length(); i++) {
			char c = raw.charAt(i);
			if (c != AMPERSAND)
				sb.append(c);
