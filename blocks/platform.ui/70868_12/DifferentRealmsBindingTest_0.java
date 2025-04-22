
	@Test
	public void testBindingCanBeCreatedOutsideOfValidationRealm() throws Exception {
		final ObservableSet<String> model = new WritableSet<>(targetAndModelRealm);
		final ObservableSet<String> target = new WritableSet<>(targetAndModelRealm);

		targetAndModelRealm.unblock();

		AtomicReference<Exception> exceptionOccured = new AtomicReference<>();
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					dbc.bindSet(target, model);
				} catch (Exception e) {
					exceptionOccured.set(e);
				}
			}
		};
		t.start();
		t.join(1000);

		assertNull(exceptionOccured.get());
	}
