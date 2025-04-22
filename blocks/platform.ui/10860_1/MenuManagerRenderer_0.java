	private EventHandler childUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu))
				return;

			Object menuModel = event.getProperty(UIEvents.EventTags.ELEMENT);

			if (UIEvents.isADD(event)) {
				processContents((MElementContainer<MUIElement>) menuModel);
			} else if (UIEvents.isREMOVE(event)) {
				MenuManager parentManager = getManager((MMenu) menuModel);
				if (parentManager == null) {
					return;
				}
				Object oldValue = event
						.getProperty(UIEvents.EventTags.OLD_VALUE);
				if (oldValue instanceof MMenu) {
					cleanUp((MMenu) oldValue);
				} else if (oldValue instanceof MMenuElement) {
					disposeContributionItem(
							getContribution((MMenuElement) oldValue),
							parentManager, (MMenuElement) oldValue);
					parentManager.update(false);
				} else if (oldValue instanceof List) {
					for (Object object : (List) oldValue) {
						if (object instanceof MMenu) {
							cleanUp((MMenu) object);
						} else if (object instanceof MMenuElement) {
							disposeContributionItem(
									getContribution((MMenuElement) object),
									parentManager, (MMenuElement) object);
						}
					}
					parentManager.update(false);
				}
			}
		}
	};

