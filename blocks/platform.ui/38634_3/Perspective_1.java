	protected void addAlwaysOff(IActionSetDescriptor descriptor) {
		if (descriptor == null) {
			return;
		}
		if (alwaysOffActionSets.contains(descriptor)) {
			return;
		}
		alwaysOffActionSets.add(descriptor);
		if (page != null) {
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_MASK);
		}
		removeAlwaysOn(descriptor);
	}
