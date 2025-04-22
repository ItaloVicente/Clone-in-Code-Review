		IPropertyChangeListener listener= new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (key.equals(event.getProperty()) && value.equals(event.getOldValue())) {
					found[0] = true;
				}
