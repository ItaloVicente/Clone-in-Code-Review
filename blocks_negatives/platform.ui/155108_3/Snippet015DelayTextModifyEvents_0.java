		final IObservableValue<Boolean> stale1 = Observables.observeStale(delayed1);
		new ControlUpdater(field2) {
			@Override
			protected void updateControl() {
				field2.setFont(stale1.getValue() ? italicFont : shellFont);
			}
		};

		final IObservableValue<Boolean> stale2 = Observables.observeStale(delayed2);
		new ControlUpdater(field1) {
			@Override
			protected void updateControl() {
				field1.setFont(stale2.getValue() ? italicFont : shellFont);
			}
		};
