
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
	public void testExecAndGetBasicSameRealm() {
		CurrentRealm realm = new CurrentRealm(true);
		String result = realm.execAndGet(() -> "result");
		assertEquals("result", result);
	}

	@Test
	public void testExecAndGetExceptionSameRealm() {
		CurrentRealm realm = new CurrentRealm(true);
		RuntimeException expectedException = new RuntimeException();

		try {
			realm.execAndGet(() -> {
				throw expectedException;
			});
			fail("Should throw");
		} catch (RuntimeException e) {
			assertSame(expectedException, e);
		}
	}

	@Test
	public void testExecAndGetException() {
		runAsyncRealmTest(realm -> {
			RuntimeException expectedException = new RuntimeException();
			try {
				realm.execAndGet(() -> {
					throw expectedException;
				});
				fail("Should throw");
			} catch (RuntimeException e) {
				assertSame(expectedException, e);
			}
		});
	}

	@Test
	public void testExecAndGetUnknownError() {
		runAsyncRealmTest(realm -> {
			Error thrownError = new Error();
			try {
				realm.execAndGet(() -> {
					throw thrownError;
				});
				fail("Should throw");
			} catch (Error e) {
				assertNotSame(thrownError, e);
			}
		});
	}

	@Test
	public void testExecAndGetAssertionError() {
		runAsyncRealmTest(realm -> {
			Error expectedError = new AssertionError();
			try {
				realm.execAndGet(() -> {
					throw expectedError;
				});
				fail("Should throw");
			} catch (AssertionError e) {
				assertSame(expectedError, e);
			}
		});
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
