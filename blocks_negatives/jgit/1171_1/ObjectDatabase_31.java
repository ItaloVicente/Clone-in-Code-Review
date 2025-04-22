	public void closeSelf() {
	}

	/** Fully close all loaded alternates and clear the alternate list. */
	public final void closeAlternates() {
		ObjectDatabase[] alt = alternates.get();
		if (alt != null) {
			alternates.set(null);
			closeAlternates(alt);
		}
	}
