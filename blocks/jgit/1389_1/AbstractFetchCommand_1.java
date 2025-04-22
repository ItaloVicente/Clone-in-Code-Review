	private String safeAbbreviate(ObjectReader reader
		try {
			return reader.abbreviate(id).name();
		} catch (IOException cannotAbbreviate) {
			return id.name();
		}
	}

