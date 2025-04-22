
	private void checkOptions() {
		if ((ours || theirs) && !isCheckoutIndex())
			throw new IllegalStateException(
					"Checking out ours/theirs is only possible when checking out index
							+ "not when switching branches.");
		if (ours && theirs)
			throw new IllegalStateException(
					"Can not check out 'ours' and 'theirs'
	}
