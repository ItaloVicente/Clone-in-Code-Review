		checkRealm();
		pauseDepth--;
		if (pauseDepth < 0) {
			throw new IllegalStateException(
					"Resume has unessesarily been called too often, so that the pauseDepth is below zero."); //$NON-NLS-1$
		} else if (dirty && pauseDepth == 0) {
