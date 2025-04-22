	REVERTING {
		@Override
		public boolean canCheckout() { return false; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return false; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_conflicts; }
	}

	REVERTING_RESOLVED {
		@Override
		public boolean canCheckout() { return true; }

		@Override
		public boolean canResetHead() { return true; }

		@Override
		public boolean canCommit() { return true; }

		@Override
		public boolean canAmend() { return false; }

		@Override
		public String getDescription() { return JGitText.get().repositoryState_merged; }
	}

