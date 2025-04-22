        }
        return null;
    }

    private Map updateIdentifiers(Collection identifierIds) {
        return updateIdentifiers(identifierIds, definedActivityIds);
    }
    
    private Map updateIdentifiers(Collection identifierIds, Set changedActivityIds) {
        Map identifierEventsByIdentifierId = new TreeMap();

        for (Iterator iterator = identifierIds.iterator(); iterator.hasNext();) {
            String identifierId = (String) iterator.next();
            Identifier identifier = (Identifier) identifiersById
                    .get(identifierId);

            if (identifier != null) {
                IdentifierEvent identifierEvent = updateIdentifier(identifier, changedActivityIds);

                if (identifierEvent != null) {
					identifierEventsByIdentifierId.put(identifierId,
                            identifierEvent);
