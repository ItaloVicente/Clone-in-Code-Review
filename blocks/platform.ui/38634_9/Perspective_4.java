	private void removeAlwaysOff(IActionSetDescriptor descriptor) {
		if (!alwaysOffActionSets.contains(descriptor)) {
			return;
		}
		alwaysOffActionSets.remove(descriptor);
		page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
	}
