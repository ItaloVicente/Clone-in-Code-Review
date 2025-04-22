    	RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				VetoableValueStub observable = new VetoableValueStub();
				observable.fireValueChanging(null);
			}
    	});
