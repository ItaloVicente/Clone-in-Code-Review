	private void removeAlwaysOff(IActionSetDescriptor descriptor) {
		if (descriptor == null) {
			return;
		}
		if (!alwaysOffActionSets.contains(descriptor)) {
			return;
		}
		alwaysOffActionSets.remove(descriptor);
		if (page != null) {
			page.perspectiveActionSetChanged(this, descriptor, ActionSetManager.CHANGE_UNMASK);
		}
	}
