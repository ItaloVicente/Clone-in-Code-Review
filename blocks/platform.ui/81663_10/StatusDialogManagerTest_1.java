	public void testDeadlockFromBug501681() throws Exception {
		assertNotNull("Test must run in UI thread", Display.getCurrent());

		final StatusAdapter statusAdapter = createStatusAdapter("Oops");
		AtomicReference<StatusAdapter[]> reported = new AtomicReference<>();
		INotificationListener listener = (type, adapters) -> {
			reported.set(adapters);
		};

		AtomicReference<ReentrantLock> lock = new AtomicReference<>();
		lock.set(new ReentrantLock());
		final Semaphore semaphore = new Semaphore(0);

		Thread t = new Thread(() -> {
			lock.get().lock();
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
			}
			lock.get().unlock();
		});
		t.start();

		while (!lock.get().isLocked()) {
			Thread.sleep(50);
		}
		assertTrue(lock.get().isLocked());

		StatusManager.getManager().addListener(listener);
		StatusManager.getManager().handle(statusAdapter, StatusManager.SHOW | StatusManager.LOG);
		assertSame(statusAdapter, reported.get()[0]);
		Shell shell = StatusDialogUtil.getStatusShell();
		if (shell != null) {
			shell.dispose();
		}
		reported.set(null);

		final StatusAdapter statusAdapter2 = createStatusAdapter("Oops2");
		try {
			Job badJob = new Job("Will report blocking error") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					StatusManager.getManager().handle(statusAdapter2, StatusManager.BLOCK | StatusManager.LOG);
					semaphore.release();
					return Status.OK_STATUS;
				}
			};

			badJob.schedule(100);

			lock.get().lock();

			badJob.join();
		} finally {
			postUnblockingTask();

			UITestCase.processEvents();
			StatusManager.getManager().removeListener(listener);
		}
		assertFalse("Job should successfully finish", semaphore.hasQueuedThreads());
		assertNotNull("Status adapter was not logged", reported.get());
		assertSame(statusAdapter2, reported.get()[0]);
	}

	private void postUnblockingTask() {
		Display.getCurrent().timerExec(100, () -> {
			Shell shell = StatusDialogUtil.getStatusShellImmediately();
			if (shell != null) {
				shell.dispose();
			} else {
				postUnblockingTask();
			}
		});
	}
