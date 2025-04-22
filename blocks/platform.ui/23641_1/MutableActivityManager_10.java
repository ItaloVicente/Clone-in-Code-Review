		}
	}

	private void notifyIdentifiers(Map<String, IdentifierEvent> identifierEventsByIdentifierId) {
		for (Iterator iterator = identifierEventsByIdentifierId.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String identifierId = (String) entry.getKey();
			IdentifierEvent identifierEvent = (IdentifierEvent) entry.getValue();
			Identifier identifier = identifiersById.get(identifierId);

			if (identifier != null) {
