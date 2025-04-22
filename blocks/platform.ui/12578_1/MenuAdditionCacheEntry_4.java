	private class IdListener implements IIdentifierListener {
		public void identifierChanged(IdentifierEvent identifierEvent) {
			application.getContext().set(identifierEvent.getIdentifier().getId(),
					identifierEvent.getIdentifier().isEnabled());
		}
	}

	private IdListener idUpdater = new IdListener();

	private void createIdentifierTracker(MApplicationElement item) {
		if (item.getElementId() != null && item.getElementId().length() > 0) {
			String id = namespaceIdentifier + "/" + item.getElementId(); //$NON-NLS-1$
			item.getPersistedState().put(MenuManagerRenderer.VISIBILITY_IDENTIFIER, id);
			final IIdentifier identifier = activityManager.getIdentifier(id);
			if (identifier != null) {
				application.getContext().set(identifier.getId(), identifier.isEnabled());
				identifier.addIdentifierListener(idUpdater);
			}
		}
	}

