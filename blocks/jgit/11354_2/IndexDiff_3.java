	private void addConflict(String path
		StageState existingStageStates = conflicts.get(path);
		byte stageMask = 0;
		if (existingStageStates != null)
			stageMask |= existingStageStates.getStageMask();
		int shifts = stage - 1;
		stageMask |= (1 << shifts);
		StageState stageState = StageState.fromMask(stageMask);
		conflicts.put(path
	}

