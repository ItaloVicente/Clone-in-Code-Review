	}

	private void updateLabelOfMenu(Event event) {
		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
		MMenu model = (MMenu) event.getProperty(UIEvents.EventTags.ELEMENT);
		MenuManager manager = getManager(model);
		if ((manager == null)) {
			return;
