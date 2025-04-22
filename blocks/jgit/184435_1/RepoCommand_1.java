	public RepoCommand setLockFailureRetryParameters(int maxRetries
			long minRetryGapMillis
		this.lockFailureMaxRetries = maxRetries;
		this.lockFailureMinRetryDelayMillis = minRetryGapMillis;
		this.lockFailureMaxRetryDelayMillis = maxRetryGapMillis;
		return this;
	}

