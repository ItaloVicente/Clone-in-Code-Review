		final boolean isDirty;
		if (nonTree(modeF)) {
			boolean sameMode = modeF == modeO;

			if (!sameMode)
				sameMode = (FileMode.TYPE_MASK & modeF) == FileMode.TYPE_FILE
						&& FileMode.EXECUTABLE_FILE.equals(modeO)
						&& !db.getFS().supportsExecute();

			isDirty = !sameMode || !tw.idEqual(T_FILE
		} else
			isDirty = false;

