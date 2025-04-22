		propChangeListeners.add(listener);
	}

	@Override
	public void removePropertyListener(IPropertyListener listener) {
		if (isDisposed()) {
			return;
		}
		propChangeListeners.remove(listener);
	}
