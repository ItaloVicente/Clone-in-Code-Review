		Set result = new IdentitySet(Arrays.asList(ObservableTracker.runAndMonitor(() -> {
			ObservableTracker.getterCalled(observables[0]); // monitored
			ObservableTracker.setIgnore(true);
			ObservableTracker.getterCalled(observables[1]); // ignored
			ObservableTracker.setIgnore(true);
			ObservableTracker.getterCalled(observables[2]); // ignored
			ObservableTracker.setIgnore(false);
			ObservableTracker.getterCalled(observables[3]); // ignored
			ObservableTracker.setIgnore(false);
			ObservableTracker.getterCalled(observables[4]); // monitored
