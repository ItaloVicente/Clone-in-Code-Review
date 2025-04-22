	public void turnOffActionSets(IActionSetDescriptor[] toDisable) {
		for (int i = 0; i < toDisable.length; i++) {
			IActionSetDescriptor descriptor = toDisable[i];
			turnOffActionSet(descriptor);
		}
	}
