		boolean isDirty;
		if (ourDce != null)
			isDirty = work.isModified(ourDce
		else {
			isDirty = work.isModeDifferent(modeO);
			if (!isDirty && nonTree(modeF))
				isDirty = !tw.idEqual(T_FILE
		}

