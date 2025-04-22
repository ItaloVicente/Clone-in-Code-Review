		Set collected = new IdentitySet(Arrays.asList(ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				list.add(new ObservableStub()); // list[0] collected
				ObservableTracker.setIgnore(true);
				list.add(new ObservableStub()); // list[1] ignored
				ObservableTracker.setIgnore(true);
				list.add(new ObservableStub()); // list[2] ignored
				ObservableTracker.setIgnore(false);
				list.add(new ObservableStub()); // list[3] ignored
				ObservableTracker.setIgnore(false);
				list.add(new ObservableStub()); // list[4] collected
			}
		})));
