	private EventHandler childUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			final Object obj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(obj instanceof MMenu) || (obj instanceof MOpaqueMenu))
				return;

			MMenu menuModel = (MMenu) obj;

			if (UIEvents.isADD(event)) {
				processContents((MElementContainer<MUIElement>) ((Object) menuModel));
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

