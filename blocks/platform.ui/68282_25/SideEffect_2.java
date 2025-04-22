		checkRealm();
		pauseDepth--;
		if (pauseDepth < 0) {
			throw new IllegalStateException(
					"The resume() method was called more often than pause()."); //$NON-NLS-1$
		} else if (dirty && pauseDepth == 0) {
