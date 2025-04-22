	CHERRY_PICKING {
		public boolean canCheckout() { return false; }
		public boolean canResetHead() { return true; }
		public boolean canCommit() { return false; }
		public String getDescription() { return JGitText.get().repositoryState_conflicts; }
	}

	CHERRY_PICKING_RESOLVED {
		public boolean canCheckout() { return true; }
		public boolean canResetHead() { return true; }
		public boolean canCommit() { return true; }
		public String getDescription() { return JGitText.get().repositoryState_merged; }
	}

