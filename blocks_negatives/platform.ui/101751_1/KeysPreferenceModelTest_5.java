		final ArrayList events = new ArrayList();
		controller.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				events.add(event);
			}
		});
