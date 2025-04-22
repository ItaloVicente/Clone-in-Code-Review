		IPropertyChangeListener failingListener = new  IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				fail("1.0");
			}
		};
