	private void removeAlwaysOn(IActionSetDescriptor descriptor) {
		if (alwaysOnActionSets.contains(descriptor)) {
			alwaysOnActionSets.remove(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_HIDE);
		}
	}
