	private void overrideActionBars() {
		if (registry.getActionProvider() != null ) {
			IActionProvider actionProvider = registry.getActionProvider();
			actionProvider.setActionBars(contributor, getSite().getActionBars());
		}
	}
