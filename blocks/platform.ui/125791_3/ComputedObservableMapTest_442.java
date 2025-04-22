		private PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				fireSingleChange(evt.getSource(), evt.getOldValue(), evt
						.getNewValue());
			}
		};
