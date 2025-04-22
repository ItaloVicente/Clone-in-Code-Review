	public void testSideEffectFiresDisposeEvent() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();

		ISideEffect disposeTest = ISideEffect.getFactory().createPaused(() -> {
		});
		disposeTest.resume();
		disposeTest.addDisposeListener(sideEffect -> {
			assertTrue(disposeTest == sideEffect);
			hasRun.set(true);
		});
		disposeTest.dispose();
		runAsync();
		assertTrue(hasRun.get());
	}

	public void testCanRemoveDisposeListener() throws Exception {
		AtomicBoolean hasRun = new AtomicBoolean();

		ISideEffect disposeTest = ISideEffect.getFactory().createPaused(() -> {
		});
		disposeTest.resume();
		Consumer<ISideEffect> disposeListener = sideEffect -> {
			hasRun.set(true);
		};
		disposeTest.addDisposeListener(disposeListener);
		disposeTest.removeDisposeListener(disposeListener);
		disposeTest.dispose();
		runAsync();
		assertFalse(hasRun.get());
	}

