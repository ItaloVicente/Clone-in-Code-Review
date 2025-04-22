
	@Test(timeout = 5000)
	public void testDisposingBothsObservablesConcurrently() throws InterruptedException {
		final CountDownLatch latch1 = new CountDownLatch(1);
		final CountDownLatch latch2 = new CountDownLatch(1);
		ThreadRealm targetRealm = createRealm();
		final IObservableSet<String> target = new WritableSet<String>(targetRealm) {
			@Override
			public synchronized void dispose() {
				super.dispose();

			}
			@Override
			public synchronized void removeDisposeListener(IDisposeListener listener) {
				latch1.countDown();
				try {
					latch2.await();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new RuntimeException();
				}
			}
		};
		ThreadRealm modelRealm = createRealm();
		final IObservableSet<String> model = new WritableSet<>(modelRealm);

		ThreadRealm validationRealm = createRealm();
		DataBindingContext context = new DataBindingContext(validationRealm);

		context.getValidationRealm()
				.exec(() -> context.bindSet(target, model, new UpdateSetStrategy(UpdateSetStrategy.POLICY_NEVER),
				new UpdateSetStrategy(UpdateSetStrategy.POLICY_NEVER)));
		validationRealm.processQueue();

		targetRealm.exec(() -> target.dispose());
		targetRealm.processQueue();

		assertTrue(latch1.await(2, TimeUnit.SECONDS));

		modelRealm.exec(() -> model.dispose());
		modelRealm.processQueue();

		assertTrue(errorStatusses.toString(), errorStatusses.isEmpty());
	}

	private ThreadRealm createRealm() {
		final ThreadRealm realm = new ThreadRealm();
		new Thread(() -> {
			realm.init(Thread.currentThread());
			realm.block();
		}).start();
		return realm;
	}
