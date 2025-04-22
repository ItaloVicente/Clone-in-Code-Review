	public BranchRebaseMode getSelectedRebaseMode() {
		if (!configureUpstreamCheck.getSelection()) {
			return null;
		} else {
			return rebase.getRebaseMode();
		}
