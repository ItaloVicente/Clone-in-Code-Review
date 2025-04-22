		if (focusDelegatingInProgress) {
			if (logger != null && logger.isDebugEnabled()) {
				logger.debug("Ignored attempt to set focus during set focus for: " + this); //$NON-NLS-1$
			}
			return;
		}
		focusDelegatingInProgress = true;
