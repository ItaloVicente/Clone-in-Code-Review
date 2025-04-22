		this.contiguousCommitCount = config.getBitmapContiguousCommitCount();
		this.recentCommitCount = config.getBitmapRecentCommitCount();
		this.recentCommitSpan = config.getBitmapRecentCommitSpan();
		this.distantCommitSpan = config.getBitmapDistantCommitSpan();
		this.excessiveBranchCount = config.getBitmapExcessiveBranchCount();
		long now = SystemReader.getInstance().getCurrentTime();
		long ageInSeconds = config.getBitmapInactiveBranchAgeInDays() * 24 * 60 * 60;
		this.inactiveBranchTimestamp = (now / 1000) - ageInSeconds;
