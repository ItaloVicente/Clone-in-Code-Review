	/**
	 * Configure a {@link GitServlet} that faces a {@link IllegalStateException}
	 * during executing preReceiveHooks. This used to lead to exceptions with a
	 * description of "invalid channel 101" on the client side. Make sure
	 * clients receive the correct response on the correct sideband.
	 *
	 * @throws Exception
	 */
	@Test
	public void testRuntimeExceptionInPreReceiveHook() throws Exception {
		final TestRepository client = createTestRepository();
		final RevBlob Q_txt = client
				.blob("some blob content to measure pack size");
		final RevCommit Q = client.commit().add("Q", Q_txt).create();
		final Repository clientRepo = client.getRepository();
		final String srvBranchName = Constants.R_HEADS + "new.branch";
		Transport t;

		maxPackSize = 0;
		postHook = null;
		preHook = new PreReceiveHook() {
			@Override
			public void onPreReceive(ReceivePack rp,
					Collection<ReceiveCommand> commands) {
				throw new IllegalStateException();
			}
		};

		t = Transport.open(clientRepo, srvURI);
		try {
			RemoteRefUpdate update = new RemoteRefUpdate(clientRepo, Q.name(),
					srvBranchName, false, null, null);
			try {
				t.push(NullProgressMonitor.INSTANCE,
						Collections.singleton(update));
				fail("should not reach this line");
			} catch (Exception e) {
				assertTrue(e instanceof TransportException);
			}
		} finally {
			t.close();
		}
	}

	/**
	 * Configure a {@link GitServlet} that faces a {@link IllegalStateException}
	 * during executing objectChecking.
	 *
	 * @throws Exception
	 */
	@Test
	public void testObjectCheckerException() throws Exception {
		final TestRepository client = createTestRepository();
		final RevBlob Q_txt = client
				.blob("some blob content to measure pack size");
		final RevCommit Q = client.commit().add("Q", Q_txt).create();
		final Repository clientRepo = client.getRepository();
		final String srvBranchName = Constants.R_HEADS + "new.branch";
		Transport t;

		maxPackSize = 0;
		postHook = null;
		preHook = null;
		oc = new ObjectChecker() {
			@Override
			public void checkCommit(byte[] raw) throws CorruptObjectException {
				throw new IllegalStateException();
			}
		};

		t = Transport.open(clientRepo, srvURI);
		try {
			RemoteRefUpdate update = new RemoteRefUpdate(clientRepo, Q.name(),
					srvBranchName, false, null, null);
			try {
				t.push(NullProgressMonitor.INSTANCE,
						Collections.singleton(update));
				fail("should not reach this line");
			} catch (Exception e) {
				assertTrue(e instanceof TransportException);
			}
		} finally {
			t.close();
		}
	}

	/**
	 * Configure a {@link GitServlet} that faces a {@link TooLargePackException}
	 * during persisting the pack and a {@link IllegalStateException} during
	 * executing postReceiveHooks. This used to lead to exceptions with a
	 * description of "invalid channel 101" on the client side. Make sure
	 * clients receive the correct response about the too large pack on the
	 * correct sideband.
	 *
	 * @throws Exception
	 */
