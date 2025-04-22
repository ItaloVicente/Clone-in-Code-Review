	public void handleStateChange(State state, Object oldValue) {
		try {
			this.verboseBranchMode = ((Boolean) state.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

