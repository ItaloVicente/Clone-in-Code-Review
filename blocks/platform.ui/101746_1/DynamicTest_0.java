		enabledIdentifier.addIdentifierListener(identifierEvent -> {
			switch (listenerType) {
			case ACTIVITY_ENABLED_CHANGED:
				assertTrue(identifierEvent.hasEnabledChanged());
				break;
			case ACTIVITY_IDS_CHANGED:
				assertTrue(identifierEvent.hasActivityIdsChanged());
				break;
			}
			listenerType = -1;
		});
