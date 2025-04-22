	/**
	 * Fully delegates the size computation to the internal layout manager.
	 */
	@Override
	public final Point computeSize(int wHint, int hHint, boolean changed) {
		return ((FormLayout) getLayout()).computeSize(this, wHint, hHint,
				changed);
	}

