		IndexDiffChangedListener[] tmpListeners;
		synchronized (listeners) {
			tmpListeners = listeners
					.toArray(new IndexDiffChangedListener[0]);
		}
		for (int i = 0; i < tmpListeners.length; i++) {
			try {
				tmpListeners[i].indexDiffChanged(repository, indexDiffData);
			} catch (RuntimeException e) {
				Activator.logError(
						"Exception occured in an IndexDiffChangedListener", e); //$NON-NLS-1$
			}
