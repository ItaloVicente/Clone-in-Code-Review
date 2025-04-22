			IPropertyChangeListener badListener = new IPropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty() != IWorkingSetManager.CHANGE_WORKING_SET_ADD) {
						return;
					}
					Object ws = event.getNewValue();
					if (!(ws instanceof AggregateWorkingSet)) {
						return;
