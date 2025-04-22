	public void handleStateChange(State state, Object oldValue) {
		try {
			this.branchHierarchyMode = ((Boolean) state.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, false);
		}
	}
