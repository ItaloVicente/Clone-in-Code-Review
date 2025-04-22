		}
	}

	private void notifyIdentifiers(Map<String, IdentifierEvent> identifierEventsByIdentifierId) {
		for (Iterator<Entry<String, IdentifierEvent>> iterator = identifierEventsByIdentifierId.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<String, IdentifierEvent> entry = iterator.next();
			String identifierId = entry.getKey();
			IdentifierEvent identifierEvent = entry.getValue();
			Identifier identifier = identifiersById.get(identifierId);

			if (identifier != null) {
