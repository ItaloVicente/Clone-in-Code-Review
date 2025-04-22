				dependencies = ObservableTracker.runAndMonitor(new Runnable() {
					@Override
					public void run() {
						updateItem(indexOfItem, item, element);
					}
				}, this, null);
