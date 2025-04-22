			enabledChanged = identifier.setEnabled(enabled);

			if (activityIdsChanged || enabledChanged) {
				return new IdentifierEvent(identifier, activityIdsChanged, enabledChanged);
			}
		}
		return null;
	}

	private Map<String, IdentifierEvent> updateIdentifiers(Collection<String> identifierIds) {
		return updateIdentifiers(identifierIds, definedActivityIds);
	}

	private Map<String, IdentifierEvent> updateIdentifiers(Collection<String> identifierIds,
			Set<String> changedActivityIds) {
		Map<String, IdentifierEvent> identifierEventsByIdentifierId = new TreeMap<String, IdentifierEvent>();

		for (Iterator<String> iterator = identifierIds.iterator(); iterator.hasNext();) {
			String identifierId = iterator.next();
			Identifier identifier = identifiersById.get(identifierId);

			if (identifier != null) {
				IdentifierEvent identifierEvent = updateIdentifier(identifier, changedActivityIds);

				if (identifierEvent != null) {
					identifierEventsByIdentifierId.put(identifierId, identifierEvent);
