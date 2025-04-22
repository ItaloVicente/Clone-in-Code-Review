	private EventHandler idChangeListener = new EventHandler() {
		public void handleEvent(Event event) {
			Object changedObject = event.getProperty(EventTags.ELEMENT);

			if (!(changedObject instanceof MPerspective))
				return;

			MPerspective perspective = (MPerspective) changedObject;

			String newID = (String) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			String oldID = (String) event.getProperty(UIEvents.EventTags.OLD_VALUE);

			newID = '(' + newID + ')';
			oldID = '(' + oldID + ')';

			MWindow perspWin = modelService.getTopLevelWindowFor(perspective);
			if (perspWin == null)
				return;

			List<MToolControl> trimStacks = modelService.findElements(perspWin, null,
					MToolControl.class, null);
			for (MToolControl trimStack : trimStacks) {
				if (TrimStack.CONTRIBUTION_URI.equals(trimStack.getContributionURI()))
					trimStack.setElementId(trimStack.getElementId().replace(oldID, newID));
