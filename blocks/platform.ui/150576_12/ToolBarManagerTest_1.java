	public void testUpdate() {
		ToolBarManager manager = new ToolBarManager();
		ObservableControlContribution item = new ObservableControlContribution("i want to be updated!");
		manager.add(item);
		manager.createControl(createComposite());
		assertFalse("Update was called already", item.updateCalled);
		assertTrue("computeWidth was not called", item.computeWidthCalled);
		item.computeWidthCalled = false;
		manager.update(false);
		assertFalse("Item update should only be called when manager update is forced", item.updateCalled);
		assertFalse("computeWidth should only be called when manager update is forced", item.computeWidthCalled);
		manager.update(true);
		assertTrue("Update was not called", item.updateCalled);
		assertTrue("computeWidth was not called", item.computeWidthCalled);
	}

