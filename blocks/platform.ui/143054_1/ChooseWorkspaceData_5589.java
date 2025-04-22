		Integer version = protocolMemento.getInteger(XML.VERSION);
		return version != null && version.intValue() == PERS_ENCODING_VERSION;
	}

	private static URL getPersistenceUrl(URL baseUrl, boolean create) {
		if (baseUrl == null) {
