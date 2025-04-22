			filterPreferenceListener = new IPropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty().equals(getMementoPreferenceName())) {
						rebuildFilters();
					}

