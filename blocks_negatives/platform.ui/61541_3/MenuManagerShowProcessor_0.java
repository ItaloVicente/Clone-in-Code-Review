	private void cleanUp(final Menu menu, MMenu menuModel,
			MenuManager menuManager) {
		trace("cleanUp", menu, null); //$NON-NLS-1$
		if (pendingCleanup.isEmpty()) {
			return;
		}
		Runnable cleanUp = pendingCleanup.remove(menu);
		if (cleanUp != null) {
			trace("cleanUp.run()", menu, null); //$NON-NLS-1$
			cleanUp.run();
		}
