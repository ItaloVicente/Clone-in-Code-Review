		@Override
		public String getBranch() {
			return branch;
		}

		@Override
		public String getBranchStatus() {
			return branchStatus;
		}

		@Override
		public boolean isTracked() {
			return tracked;
		}

		@Override
		public boolean isIgnored() {
			return ignored;
		}

		@Override
		public boolean isDirty() {
			return dirty;
		}

		@Override
		public boolean isMissing() {
			return false;
		}

		@Override
		public StagingState getStagingState() {
			return staged;
		}

		@Override
		public boolean isStaged() {
			return staged != StagingState.NOT_STAGED;
		}

		@Override
		public boolean hasConflicts() {
			return conflicts;
		}

		@Override
		public boolean isAssumeUnchanged() {
			return assumeUnchanged;
		}
