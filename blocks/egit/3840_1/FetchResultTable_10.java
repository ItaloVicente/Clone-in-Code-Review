	private String safeAbbreviate(ObjectId id) {
		String abbrev = abbrevations.get(id);
		if (abbrev == null) {
			try {
				abbrev = reader.abbreviate(id).name();
			} catch (IOException cannotAbbreviate) {
				abbrev = id.name();
