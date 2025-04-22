	private boolean isReconcileManagerToModel = false;

	private boolean isProcessContributions = false;

	private boolean disposingMenuUIElement = false;

	private EventHandler childrenUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			if (isReconcileManagerToModel || isProcessContributions)
				return;

			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (changedObj instanceof MMenu) {
				MMenu menuModel = (MMenu) changedObj;
				MenuManager manager = getManager(menuModel);
				if (manager == null)
					return;
				if (UIEvents.isREMOVE(event)) {
					@SuppressWarnings("unchecked")
					Iterable<MMenuElement> toRemove = (Iterable<MMenuElement>) UIEvents
							.asIterable(event, UIEvents.EventTags.OLD_VALUE);

					handleMenuElementRemove(manager, toRemove);
				} else if (UIEvents.isADD(event)) {
					@SuppressWarnings("unchecked")
					Iterable<MMenuElement> toAdd = (Iterable<MMenuElement>) UIEvents
							.asIterable(event, UIEvents.EventTags.NEW_VALUE);
					handleMenuElementAdd(manager, toAdd);
				}
			}
		}
	};

