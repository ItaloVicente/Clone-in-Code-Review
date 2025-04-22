
	private BatchRefUpdate newBatchUpdate(List<ReceiveCommand> cmds) {
		BatchRefUpdate u = refdir.newBatchUpdate();
		if (atomic) {
			assertTrue(u.isAtomic());
		} else {
			u.setAtomic(false);
		}
		u.addCommand(cmds);
		return u;
	}

	private void execute(BatchRefUpdate u
		try (RevWalk rw = new RevWalk(diskRepo)) {
			u.execute(rw
					strictWork ? new StrictWorkMonitor() : NullProgressMonitor.INSTANCE);
		}
	}

	private void assertRefs(Object... args) throws IOException {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException(
					"expected even number of args: " + Arrays.toString(args));
		}

		Map<String
		for (int i = 0; i < args.length; i += 2) {
			expected.put((String) args[i]
		}

		Map<String
		Ref actualHead = refs.remove(Constants.HEAD);
		if (actualHead != null) {
			assertEquals("refs/heads/master"
			AnyObjectId expectedMaster = expected.get("refs/heads/master");
			assertNotNull("expected master ref since HEAD exists"
			assertEquals(expectedMaster
		}

		Map<String
		refs.forEach((n

		assertEquals(expected.keySet()
		actual.forEach((n
	}

	enum Result {
		OK(ReceiveCommand.Result.OK)
		LOCK_FAILURE(ReceiveCommand.Result.LOCK_FAILURE)
		REJECTED_NONFASTFORWARD(ReceiveCommand.Result.REJECTED_NONFASTFORWARD)
		REJECTED_MISSING_OBJECT(ReceiveCommand.Result.REJECTED_MISSING_OBJECT)
		TRANSACTION_ABORTED(ReceiveCommand::isTransactionAborted);

		final Predicate<? super ReceiveCommand> p;

		private Result(Predicate<? super ReceiveCommand> p) {
			this.p = p;
		}

		private Result(ReceiveCommand.Result result) {
			this(c -> c.getResult() == result);
		}
	}

	private void assertResults(
			List<ReceiveCommand> cmds
		if (expected.length != cmds.size()) {
			throw new IllegalArgumentException(
					"expected " + expected.length + " result args");
		}
		for (int i = 0; i < cmds.size(); i++) {
			ReceiveCommand c = cmds.get(i);
			Result r = expected[i];
			assertTrue(
					String.format(
							"result of command (%d) on %s should be %s"
							Integer.valueOf(i)
					r.p.test(c));
		}
	}
