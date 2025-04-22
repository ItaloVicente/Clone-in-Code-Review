	private static HashMap<String
		return mkmap(a
	}

	private static HashMap<String
		if ((args.length % 2) > 0)
			throw new IllegalArgumentException("needs to be pairs");

		HashMap<String
		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i]
		}

		return map;
	}

