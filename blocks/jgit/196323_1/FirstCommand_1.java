			return new FirstCommand(line
					Collections.<String
		}
		Map<String

		for (String c : splitCapablities) {
			if (c.startsWith(OPTION_SESSION_ID)) {
				options.put(OPTION_SESSION_ID
						c.substring(OPTION_SESSION_ID.length()));
			} else {
				options.put(c
			}
