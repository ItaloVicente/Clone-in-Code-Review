
	public boolean isAnythingSaved() {
		for (Saveable savable : saveCandidates)
			if (!savable.isDirty())
				return true;
		return false;
	}
