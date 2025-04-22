			if (resultListener != null) {
				result = null;
				action.removePropertyChangeListener(resultListener);
			}
			if (trace) {
				System.out.println((System.currentTimeMillis() - ms) + " ms to run action: " + action.getText()); //$NON-NLS-1$
			}
		} else {
			if (callback != null) {
				callback.notEnabled(action, new NotEnabledException(action.getText() + " is not enabled.")); //$NON-NLS-1$
