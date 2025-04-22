	private void addAlwaysOff(IActionSetDescriptor descriptor) {
		if (!alwaysOffActionSets.contains(descriptor)) {
			alwaysOffActionSets.add(descriptor);
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
			removeAlwaysOn(descriptor);
		}
	}
