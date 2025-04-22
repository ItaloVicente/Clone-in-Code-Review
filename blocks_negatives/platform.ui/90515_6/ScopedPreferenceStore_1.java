		final PropertyChangeEvent event = new PropertyChangeEvent(this, name,
				oldValue, newValue);
		for (int i = 0; i < list.length; i++) {
			final IPropertyChangeListener listener = (IPropertyChangeListener) list[i];
			SafeRunner.run(new SafeRunnable(JFaceResources
						@Override
						public void run() {
							listener.propertyChange(event);
						}
					});
