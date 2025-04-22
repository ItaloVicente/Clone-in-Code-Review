		Map<String
		try {
			refs = local.getRefDatabase().getRefs(ALL);
		} catch (IOException e) {
			throw new TransportException(e.getMessage()
		}
		for (final Ref r : refs.values()) {
