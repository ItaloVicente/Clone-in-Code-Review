	private EventHandler labelUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu))
				return;

			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			MMenu model = (MMenu) event.getProperty(UIEvents.EventTags.ELEMENT);
			MenuManager manager = getManager(model);
			Menu menu = manager.getMenu();
			if ((menu == null) || (menu.getParentItem() == null))
				return;
			if (UIEvents.UILabel.LABEL.equals(attName)) {
				menu.getParentItem().setText(getText(model));
			}
			if (UIEvents.UILabel.ICONURI.equals(attName)) {
				menu.getParentItem().setImage(getImage(model));
			}
		}
	};

