	private EventHandler childrenUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (changedObj instanceof MMenu) {
				MMenu menuModel = (MMenu) changedObj;
				MenuManager manager = getManager(menuModel);
				if (manager == null)
					return;
				if (UIEvents.isREMOVE(event)) {
					handleMenuElementRemove(manager,
							getMenuElements(event
									.getProperty(UIEvents.EventTags.OLD_VALUE)));
				} else if (UIEvents.isADD(event)) {
					handleMenuElementAdd(manager,
							getMenuElements(event
									.getProperty(UIEvents.EventTags.NEW_VALUE)));
				}
			}
		}

		private List<MMenuElement> getMenuElements(Object eventPropertyValue) {
			if (eventPropertyValue instanceof List) {
				@SuppressWarnings("unchecked")
				List<MMenuElement> menuElements = (List<MMenuElement>) eventPropertyValue;
				return menuElements;
			} else if (eventPropertyValue instanceof MMenuElement) {
				return Arrays.asList(((MMenuElement) eventPropertyValue));
			}

			return Collections.emptyList();
		}
	};

