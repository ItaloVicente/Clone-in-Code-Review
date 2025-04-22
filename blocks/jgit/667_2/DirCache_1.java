
	public boolean hasUnmergedPaths() {
		for (int i = 0; i < entryCnt; i++) {
			if (sortedEntries[i].getStage() > 0) {
				return true;
			}
		}
		return false;
	}
