
	PersonIdent parseAuthor(byte[] raw) {
		if (raw.length == 0)
			return null;

		Map<String
		for (int p = 0; p < raw.length;) {
			int end = RawParseUtils.nextLF(raw
			if (end == p)
				break;
			int equalsIndex = RawParseUtils.next(raw
			if (equalsIndex == end)
				break;
			String key = RawParseUtils.decode(raw
			String value = RawParseUtils.decode(raw
			p = end;
			keyValueMap.put(key
		}

		String name = keyValueMap.get(GIT_AUTHOR_NAME);
		String email = keyValueMap.get(GIT_AUTHOR_EMAIL);
		String time = keyValueMap.get(GIT_AUTHOR_DATE);

		long when = Long.parseLong(time.substring(0
		String tzOffsetString = time.substring(time.indexOf(' ') + 1);
		int multiplier = -1;
		if (tzOffsetString.charAt(0) == '+')
			multiplier = 1;
		int hours = Integer.parseInt(tzOffsetString.substring(1
		int minutes = Integer.parseInt(tzOffsetString.substring(3
		int tz = (hours * 60 + minutes) * multiplier;
		if (name != null && email != null)
			return new PersonIdent(name
		return null;
	}
