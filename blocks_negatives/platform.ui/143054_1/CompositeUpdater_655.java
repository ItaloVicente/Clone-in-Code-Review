				dependencies = ObservableTracker.runAndMonitor(new Runnable() {
					@Override
					public void run() {
						updateWidget(widget, element);
					}
				}, this, null);
