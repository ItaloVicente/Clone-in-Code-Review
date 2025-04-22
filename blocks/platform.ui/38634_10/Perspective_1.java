	private void removeAlwaysOn(IActionSetDescriptor descriptor) {
		if (!alwaysOnActionSets.contains(descriptor)) {
			return;
		}
		alwaysOnActionSets.remove(descriptor);
		page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_HIDE);
	}
