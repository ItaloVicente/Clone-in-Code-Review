		Set collected = new IdentitySet(Arrays.asList(ObservableTracker
				.runAndCollect(new Runnable() {
					@Override
					public void run() {
						ObservableTracker.setIgnore(true);
						ObservableTracker.setIgnore(true);
						ObservableTracker.setIgnore(false);
						ObservableTracker.setIgnore(false);
					}
				})));
