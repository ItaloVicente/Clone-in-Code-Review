	private void addAlwaysOff(IActionSetDescriptor descriptor) {
		if (alwaysOffActionSets.contains(descriptor)) {
			return;
		}
		alwaysOffActionSets.add(descriptor);
		page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
		removeAlwaysOn(descriptor);
	}
