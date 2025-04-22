		Map<String

		for (String c : splitCapablities) {
			if (i != -1
					&& knownKeyValueCapabilities.contains(c.substring(0
				options.put(c.substring(0
			} else {
				options.put(c
			}
		}

		return new FirstCommand(line.substring(0
				Collections.<String
