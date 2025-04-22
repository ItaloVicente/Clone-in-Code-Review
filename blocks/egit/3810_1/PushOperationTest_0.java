	@Test
	public void testInvalidUriDuringPush() throws Exception {
		ILog log = Activator.getDefault().getLog();
		LogListener listener = new LogListener();
		log.addLogListener(listener);

		PushOperation pop = createInvalidPushOperation();
		pop.run(new NullProgressMonitor());
		PushOperationResult result = pop.getOperationResult();
		String errorMessage = result.getErrorMessage(new URIish(INVALID_URI));
		assertNotNull(errorMessage);
		assertTrue(errorMessage.contains(INVALID_URI));

		assertTrue(listener.loggedSomething());
		assertTrue(listener.loggedException());

	}

	private PushOperation createInvalidPushOperation() throws Exception {
		PushOperationSpecification spec = new PushOperationSpecification();
		URIish remote = new URIish(INVALID_URI);
		Repository local = repository1.getRepository();
		RemoteRefUpdate update = new RemoteRefUpdate(local, "HEAD", "refs/heads/test",
				false, null, null);
		spec.addURIRefUpdates(remote, Collections.singletonList(update));
		PushOperation pop = new PushOperation(local, spec, false, 0);
		return pop;
	}

	private final class LogListener implements ILogListener {
		private boolean loggedSomething = false;
		private boolean loggedException = false;

		public void logging(IStatus status, String plugin) {
			loggedSomething = true;
			loggedException = status.getException() != null;

		}

		public boolean loggedSomething() {
			return loggedSomething;
		}

		public boolean loggedException() {
			return loggedException;
		}

}

