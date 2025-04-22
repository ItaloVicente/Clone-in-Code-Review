	@Test
	public void atomicUpdateRespectsInProcessLockRefsChangedEvents()
			throws Exception {
		assumeTrue(atomic);
		assumeFalse(useReftable);

		writeLooseRef("refs/heads/master"
		refdir.exactRef("refs/heads/master");
		int initialRefsChangedEvents = refsChangedEvents;

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()

		Thread t = new Thread(() -> {
			try {
				execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		ReentrantLock l = refdir.inProcessPackedRefsLock;
		l.lock();
		try {
			t.start();
			long timeoutSecs = 10;

			while (l.getQueueLength() == 0) {
				Thread.sleep(3);
			}

			l.unlock();
			t.join(SECONDS.toMillis(timeoutSecs));
		} finally {
			if (l.isHeldByCurrentThread()) {
				l.unlock();
			}
		}

		assertEquals(initialRefsChangedEvents + 1
	}

