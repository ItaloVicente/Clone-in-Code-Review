	public boolean isWaitToPreventRacyPack() {
		return waitToPreventRacyPack;
	}

	public boolean isWaitToPreventRacyPack(long packSize) {
		return isWaitToPreventRacyPack()
				&& packSize > getMinSizePreventRacyPack();
	}

	public void setWaitToPreventRacyPack(boolean waitToPreventRacyPack) {
		this.waitToPreventRacyPack = waitToPreventRacyPack;
	}

	public long getMinSizePreventRacyPack() {
		return minSizePreventRacyPack;
	}

	public void setMinSizePreventRacyPack(long minSizePreventRacyPack) {
		this.minSizePreventRacyPack = minSizePreventRacyPack;
	}

