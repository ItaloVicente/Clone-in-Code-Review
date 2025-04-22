	private void removeAlwaysOff(IActionSetDescriptor descriptor) {
		if (alwaysOffActionSets.contains(descriptor)) {
			alwaysOffActionSets.remove(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
		}
	}
