	public boolean isWaitPreventRacyPack() {
		return waitPreventRacyPack;
	}

	public boolean doWaitPreventRacyPack(long packSize) {
		return isWaitPreventRacyPack()
				&& packSize > getMinSizePreventRacyPack();
	}

	public void setWaitPreventRacyPack(boolean waitPreventRacyPack) {
		this.waitPreventRacyPack = waitPreventRacyPack;
	}

	public long getMinSizePreventRacyPack() {
		return minSizePreventRacyPack;
	}

	public void setMinSizePreventRacyPack(long minSizePreventRacyPack) {
		this.minSizePreventRacyPack = minSizePreventRacyPack;
	}

