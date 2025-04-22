		this.contiguousCommitCount = 100;
		this.recentCommitCount = 20000;
		this.recentCommitSpan = 100;
		this.distantCommitSpan = 5000;
		this.excessiveBranchCount = 100;
		long now = SystemReader.getInstance().getCurrentTime();
		long ageInSeconds = 90 * 24 * 60 * 60;
		this.inactiveBranchTimestamp = (now / 1000) - ageInSeconds;
