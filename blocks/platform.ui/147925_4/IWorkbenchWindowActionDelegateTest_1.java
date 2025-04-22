	public void testLazyInit() {
		int count = NotInitializedWorkbenchWindowActionDelegate.INIT_COUNT.intValue();
		assertEquals("Expected to see zero inits of invisible delegates", 0, count);
		count = NotInitializedWorkbenchWindowActionDelegate.INSTANCE_COUNT.intValue();
		assertEquals("Expected to see zero instances of invisible delegates", 0, count);
	}

