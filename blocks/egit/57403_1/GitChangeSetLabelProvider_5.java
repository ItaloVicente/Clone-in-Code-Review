
	@Override
	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
		listeners.add(listener);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
		super.removeListener(listener);
	}

	private void fireLabelProviderChanged(
			final LabelProviderChangedEvent event) {
		for (Object o : listeners.getListeners()) {
			final ILabelProviderListener l = (ILabelProviderListener) o;
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.labelProviderChanged(event);
				}
			});
		}
	}

	@Override
	public void dispose() {
		store.removePropertyChangeListener(uiPrefsListener);
		listeners.clear();
		super.dispose();
	}
