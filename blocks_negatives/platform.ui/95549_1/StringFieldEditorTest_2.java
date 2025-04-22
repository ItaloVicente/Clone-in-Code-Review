		IPropertyChangeListener listener = new  IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				assertEquals("foo", event.getProperty());
				assertEquals("???", event.getOldValue());
				assertEquals("bar", event.getNewValue());
			}
