		Map<String

		for (String c : splitCapablities) {
			if (c.startsWith(OPTION_SESSION_ID)) {
				options.put(OPTION_SESSION_ID
						c.substring(OPTION_SESSION_ID.length() + 1));
			} else {
				options.put(c
			}
		}

		return new FirstCommand(line.substring(0
				Collections.<String
