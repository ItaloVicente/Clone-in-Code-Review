		final PropertyChangeEvent event = new PropertyChangeEvent(this, name, oldValue, newValue);
		for (Object listener : listeners) {
			final IPropertyChangeListener propertyChangeListener = (IPropertyChangeListener) listener;
			SafeRunner.run(new SafeRunnable(JFaceResources.getString("PreferenceStore.changeError")) { //$NON-NLS-1$
				@Override
				public void run() {
					propertyChangeListener.propertyChange(event);
				}
			});
