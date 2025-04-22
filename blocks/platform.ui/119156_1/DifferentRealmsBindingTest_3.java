
	private void waitForBindingInitiated() {
		modelRealm.waitUntilBlocking();
		modelRealm.processQueue();

		targetRealm.waitUntilBlocking();
		targetRealm.processQueue();
	}
