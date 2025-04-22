		if (enabledActivityIds.size() == definedActivityIds.size()) {
			enabled = true;
			enabledChanged = identifier.setEnabled(enabled);
			identifier.setActivityIds(Collections.EMPTY_SET);
			deferredIdentifiers.add(identifier);
			getUpdateJob().schedule();
			if (enabledChanged) {
				return new IdentifierEvent(identifier, activityIdsChanged, enabledChanged);
