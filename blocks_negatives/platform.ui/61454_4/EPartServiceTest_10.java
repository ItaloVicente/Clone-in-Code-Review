	private void testSaveAll_NoHandlers(boolean confirm, boolean[] beforeDirty,
			boolean[] throwException) {
		testSaveAll_NoHandlers(confirm, beforeDirty,
				afterDirty(beforeDirty, throwException),
				isSuccessful(beforeDirty, throwException),
				saveCalled(beforeDirty, throwException), throwException);
