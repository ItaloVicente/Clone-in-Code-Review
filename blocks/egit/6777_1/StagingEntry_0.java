		CONFLICTING(EnumSet.of(Action.REPLACE_WITH_FILE_IN_GIT_INDEX, Action.REPLACE_WITH_HEAD_REVISION,
					Action.STAGE, Action.LAUNCH_MERGE_TOOL));

		private final Set<Action> availableActions;

		private State(Set<Action> availableActions) {
			this.availableActions = availableActions;
		}

		public Set<Action> getAvailableActions() {
			return availableActions;
		}
	}

	enum Action {
		REPLACE_WITH_FILE_IN_GIT_INDEX,
		REPLACE_WITH_HEAD_REVISION,
		STAGE,
		UNSTAGE,
		LAUNCH_MERGE_TOOL
