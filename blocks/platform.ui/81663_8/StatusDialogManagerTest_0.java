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
					System.out.println("testDeadlockFromBug501681: before handle()");
					StatusManager.getManager().handle(statusAdapter2, StatusManager.BLOCK | StatusManager.LOG);
					System.out.println("testDeadlockFromBug501681: after handle()");
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
			System.out.println("testDeadlockFromBug501681: finally");
			postUnblockingTask();
			System.out.println("testDeadlockFromBug501681: before processEvents");

			UITestCase.processEvents();
			System.out.println("testDeadlockFromBug501681: after processEvents");

			StatusManager.getManager().removeListener(listener);
		}
		assertFalse("Job should successfully finish", wait.get());
		assertNotNull("Status adapter was not logged", reported.get());
		assertSame(statusAdapter2, reported.get()[0]);
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
