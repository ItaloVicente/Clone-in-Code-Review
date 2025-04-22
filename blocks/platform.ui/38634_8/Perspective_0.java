	private void removeAlwaysOn(IActionSetDescriptor descriptor) {
		if (descriptor == null) {
			return;
		}
		if (!alwaysOnActionSets.contains(descriptor)) {
			return;
		}
