	private EventHandler childrenUpdater = new EventHandler() {

		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (changedObj instanceof MMenu) {
				MMenu menuModel = (MMenu) changedObj;
				MenuManager manager = getManager(menuModel);
				if (manager == null)
					return;
				if (UIEvents.isREMOVE(event)) {
					MMenuElement menuElement = (MMenuElement) event
							.getProperty(UIEvents.EventTags.OLD_VALUE);
					handleMenuElementRemove(manager, menuElement);
				} else if (UIEvents.isADD(event)) {
					MMenuElement menuElement = (MMenuElement) event
							.getProperty(UIEvents.EventTags.NEW_VALUE);
					handleMenuElementAdd(manager, menuElement);
				}
			}

		}
	};

