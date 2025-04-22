		return getJob().getState() != Job.NONE;
	}

	public boolean isBlocked() {
		return getBlockedStatus() != null;
	}

	public boolean isCanceled() {
		return canceled;
	}

	@Override
