		final boolean isDirty;
		if (nonTree(modeF))
			isDirty = work.isModeDifferent(modeO)
					|| !tw.idEqual(T_FILE, T_OURS);
		else
			isDirty = false;
