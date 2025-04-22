	@Test
	public void atomicUpdateRespectsInProcessLock() throws Exception {
		assumeTrue(atomic);

		writeLooseRef("refs/heads/master"

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
			long startNanos = System.nanoTime();

			while (l.getQueueLength() == 0) {
				long elapsedNanos = System.nanoTime() - startNanos;
				assertTrue(
						"timed out waiting for work thread to attempt to acquire lock"
						NANOSECONDS.toSeconds(elapsedNanos) < timeoutSecs);
				Thread.sleep(3);
			}

			l.unlock();
			t.join(SECONDS.toMillis(timeoutSecs));
			assertFalse(t.isAlive());
		} finally {
			if (l.isHeldByCurrentThread()) {
				l.unlock();
			}
		}

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
	}

