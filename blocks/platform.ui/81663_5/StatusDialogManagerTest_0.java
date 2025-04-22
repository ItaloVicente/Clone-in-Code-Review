	public void testDeadlockFromBug501681() throws Exception {
		assertNotNull("Test must run in UI thread", Display.getCurrent());
		final StatusAdapter statusAdapter = createStatusAdapter("Oops");
		AtomicReference<StatusAdapter[]> reported = new AtomicReference<>();
		INotificationListener listener = (type, adapters) -> {
			reported.set(adapters);
		};

		AtomicReference<ReentrantLock> lock = new AtomicReference<>();
		lock.set(new ReentrantLock());
		AtomicBoolean wait = new AtomicBoolean(true);

		Thread t = new Thread(() -> {
			lock.get().lock();
			while (wait.get()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
			lock.get().unlock();
		});
		t.start();

		while (!lock.get().isLocked()) {
			Thread.sleep(50);
		}
		assertTrue(lock.get().isLocked());

		StatusManager.getManager().addListener(listener);
		try {
			Job badJob = new Job("Will report blocking error") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					System.out.println("Blocking job: before handle()");
					StatusManager.getManager().handle(statusAdapter, StatusManager.BLOCK | StatusManager.LOG);
					System.out.println("Blocking job: after handle()");
					wait.set(false);
					return Status.OK_STATUS;
				}
			};

			badJob.schedule(100);
			System.out.println("testDeadlockFromBug501681: before locking");

			lock.get().lock();
			System.out.println("testDeadlockFromBug501681: after locking");
			badJob.join();
			System.out.println("testDeadlockFromBug501681: after joining");
		} finally {
			StatusManager.getManager().removeListener(listener);
			if (lock.get().isHeldByCurrentThread()) {
				lock.get().unlock();
			}
		}

		assertFalse("Job should successfully finish", wait.get());
		assertSame(statusAdapter, reported.get()[0]);

		postUnblockingTask();
		UITestCase.processEvents();
	}

	private void postUnblockingTask() {
		System.out.println("testDeadlockFromBug501681: postUnblockingTask");
		Display.getCurrent().timerExec(100, () -> {
			Shell shell = StatusDialogUtil.getStatusShellImmediately();
			if (shell != null) {
				System.out.println("testDeadlockFromBug501681: disposing blocking shell");
				shell.dispose();
			} else {
				postUnblockingTask();
			}
		});
	}
