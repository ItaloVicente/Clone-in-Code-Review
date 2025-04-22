		if (stagingStates.isEmpty()) {
			setStagingState(StagingState.NOT_STAGED);
		} else if (stagingStates.size() == 1) {
			StagingState state = stagingStates.iterator().next();
			if (state != null) {
				setStagingState(state);
			}
		} else {
			setStagingState(StagingState.MODIFIED);
