	private void addAlwaysOn(IActionSetDescriptor descriptor) {
		if (!alwaysOnActionSets.contains(descriptor)) {
			alwaysOnActionSets.add(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_SHOW);
			removeAlwaysOff(descriptor);
		}
	}
