	private EventHandler childUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			final Object obj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(obj instanceof MMenu) || (obj instanceof MOpaqueMenu))
				return;

			MMenu menuModel = (MMenu) obj;

			if (UIEvents.isADD(event)) {
				MenuManager parentManager = getManager(menuModel);
				if (parentManager == null) {
					return;
				}
				Object newValue = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (newValue instanceof List) {
					for (Object object : (List) newValue) {
						if (object instanceof MMenuElement) {
							modelProcessSwitch(parentManager,
									(MMenuElement) object);
						}
					}
				} else if (newValue instanceof MMenuElement) {
					modelProcessSwitch(parentManager, (MMenuElement) newValue);
				}
			} else if (UIEvents.isREMOVE(event)) {
				MenuManager parentManager = getManager(menuModel);
				if (parentManager == null) {
					return;
				}
				Object oldValue = event
						.getProperty(UIEvents.EventTags.OLD_VALUE);
				if (oldValue instanceof MMenu
						&& !(oldValue instanceof MOpaqueMenu)) {
					cleanUp((MMenu) oldValue);
					disposeMenuManager(getManager((MMenu) oldValue),
							parentManager, (MMenu) oldValue);
				} else if (oldValue instanceof MMenuElement) {
					disposeContributionItem(
							getContribution((MMenuElement) oldValue),
							parentManager, (MMenuElement) oldValue);
					parentManager.update(false);
				} else if (oldValue instanceof List) {
					for (Object object : (List) oldValue) {
						if (object instanceof MMenu
								&& !(object instanceof MOpaqueMenu)) {
							cleanUp((MMenu) object);
							disposeMenuManager(getManager((MMenu) object),
									parentManager, (MMenu) object);
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

