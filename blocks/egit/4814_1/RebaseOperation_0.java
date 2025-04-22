	private boolean refreshNeeded() {
		if (result == null)
			return true;
		if (result.getStatus() == RebaseResult.Status.UP_TO_DATE)
			return false;
		return true;
	}

