
	static String normalize(String refName) {
		if (refName == null ||
			return refName;
		}

		StringBuilder sb = new StringBuilder();
		char prev = '/';
		for (int index=0; index<refName.length(); ++index) {
			char c = refName.charAt(index);
			if (prev == '/' && c == prev)
				continue;
			sb.append(c);
			prev = c;
		}
		return sb.toString();
	}

