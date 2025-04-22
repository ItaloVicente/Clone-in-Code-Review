		if (contextListener == null) {
			throw new NullPointerException("Cannot remove a null listener."); //$NON-NLS-1$
		}

		if (listeners == null) {
			return;
		}

		listeners.remove(contextListener);

		if (listeners.isEmpty()) {
			listeners = null;
		}
	}

	@Override
