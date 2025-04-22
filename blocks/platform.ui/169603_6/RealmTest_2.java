
	@Test
	public void testExecAndGetBasic() {
		Thread mainThread = Thread.currentThread();

		runAsyncRealmTest(realm -> {
			String result = realm.execAndGet(() -> {
				assertTrue(realm.isCurrent());
				assertNotSame(mainThread, Thread.currentThread());
				return "result";
			});
			assertEquals("result", result);
		});
	}

	@Test
	public void testExecAndGetWithSafeRunnable() {
		CurrentRealm realm = new CurrentRealm(true);
		RuntimeException expectedException = new RuntimeException();
		AtomicReference<Throwable> acctualException = new AtomicReference<>();

		class B implements Supplier<Void>, ISafeRunnable {
			@Override
			public void run() throws Exception {
			}

			@Override
			public Void get() {
				throw expectedException;
			}

			@Override
			public void handleException(Throwable exception) {
				acctualException.set(exception);
			}
		}

		realm.execAndGet(new B());

		assertSame(expectedException, acctualException.get());
	}

	private static void runAsyncRealmTest(Consumer<Realm> testOp) {
		final ThreadRealm realm = new ThreadRealm();

		Thread realmThread = new Thread(() -> {
			realm.init(Thread.currentThread());
			realm.block();
		});

		realmThread.setName("Realm thread");
		realmThread.start();

		AtomicBoolean continueProcess = new AtomicBoolean(true);

		try {
			Thread processThread = new Thread(() -> {
				realm.waitUntilBlocking();
				while (continueProcess.get()) {
					realm.processQueue();
				}
			});

			processThread.setName("Process thread");
			processThread.start();

			realm.waitUntilBlocking();

			testOp.accept(realm);
		} finally {
			continueProcess.set(false);
			realm.unblock();
		}

	}
