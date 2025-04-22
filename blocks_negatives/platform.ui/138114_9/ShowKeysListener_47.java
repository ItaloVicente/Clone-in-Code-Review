		Event trigger = (Event) event.getTrigger();
		if (trigger == null) {
			return;
		}
		if (!isEnabled()) {
			return;
		}

		String formattedShortcut = getFormattedShortcut(trigger);
		if (formattedShortcut == null) {
