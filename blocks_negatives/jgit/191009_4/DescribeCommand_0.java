				w.getObjectReader().abbreviate(tip, getCappedAbbrev()).name());
	}

	private int getCappedAbbrev() {
		int len = Math.max(abbrev, 4);
		len = Math.min(len, Constants.OBJECT_ID_STRING_LENGTH);
		return len;
