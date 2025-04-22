	private void addConflict(String path
		ConflictType existingConflictType = conflicts.get(path);
		byte stageMask = 0;
		if (existingConflictType != null)
			stageMask |= existingConflictType.getStageMask();
		int shifts = stage - 1;
		stageMask |= (1 << shifts);
		ConflictType conflictType = ConflictType
				.fromStageMask(stageMask);
		conflicts.put(path
	}

