	protected void addAlwaysOn(IActionSetDescriptor descriptor) {
		if (descriptor == null) {
			return;
		}
		if (alwaysOnActionSets.contains(descriptor)) {
			return;
		}
		alwaysOnActionSets.add(descriptor);
		if (page != null) {
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_SHOW);
		}
		removeAlwaysOff(descriptor);
	}
