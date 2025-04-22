
	public void testAddListenerAndInitialSyncAreUninterruptable() {
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				if (!status.isOK()) {
					Assert.fail("The databinding logger has the not-ok status " + status);
				}
			}
		});

		model.add("first");
		new ListBinding(target, model, new UpdateListStrategy(), new UpdateListStrategy());
		model.remove(0);
	}

	public void testTargetValueIsSyncedToModelIfModelWasNotSyncedToTarget() {
		target.add("first");
		dbc.bindList(target, model, new UpdateListStrategy(POLICY_UPDATE), new UpdateListStrategy(POLICY_NEVER));
		assertEquals(model.size(), target.size());
	}
