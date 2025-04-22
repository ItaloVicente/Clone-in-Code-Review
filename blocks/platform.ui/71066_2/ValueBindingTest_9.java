	public void testAddListenerAndInitialSyncAreUninterruptable() {
		model.setValue("first");
		new ValueBinding(target, model, new UpdateValueStrategy(), new UpdateValueStrategy());
		model.setValue(null);
	}

