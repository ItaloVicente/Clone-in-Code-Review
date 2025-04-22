		final boolean isDirty;
		if (nonTree(modeF))
			isDirty = workingTreeIterator.isModeDifferent(modeO)
					|| !tw.idEqual(T_FILE
		else
			isDirty = false;

