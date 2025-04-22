		server = newRepo("server");
		client = newRepo("client");
		testProtocol = new TestProtocol<>(null,
				new ReceivePackFactory<Object>() {
					@Override
					public ReceivePack create(Object req, Repository database)
							throws ServiceNotEnabledException,
							ServiceNotAuthorizedException {
						return new ReceivePack(database);
					}
				});
		uri = testProtocol.register(ctx, server);
	}

	public static class NullOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
		}
	}

	public void testWrongOldIdDoesNotReplace() throws IOException {
		RemoteRefUpdate rru = new RemoteRefUpdate(null, null, obj2, refName,
				false, null, obj3);

		Map<String, RemoteRefUpdate> updates = new HashMap<>();
		updates.put(rru.getRemoteName(), rru);

		Transport tn = testProtocol.open(uri, client, "server");
		try {
			PushConnection connection = tn.openPush();
			try {
				connection.push(NullProgressMonitor.INSTANCE, updates);
			} finally {
				connection.close();
			}
		} finally {
			tn.close();
		}

		assertEquals(REJECTED_OTHER_REASON, rru.getStatus());
		assertEquals("invalid old id sent", rru.getMessage());
	}

	public static PacketLineIn newPacketLineIn(String input) {
		return new PacketLineIn(
				new ByteArrayInputStream(Constants.encode(input)));
	}

	@Test
	public void testNonAtomicPushWithOptions() throws Exception {
		PushResult r;
		server.setPerformsAtomicTransactions(false);
		List<String> pushOptions = Arrays.asList("Hello", "World!");
		try (Transport tn = testProtocol.open(uri, client, "server")) {
			tn.setPushAtomic(false);
			tn.setCapablePushOptions(true);
			tn.setPushOptions(pushOptions);
			r = tn.push(NullProgressMonitor.INSTANCE, commands());
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");
		System.out.println("PushOptionsTest: receivePack.getPushOptions() = "
				+ receivePack.getPushOptions());
		assertSame(pushOptions, receivePack.getPushOptions());
		assertSame(RemoteRefUpdate.Status.OK, one.getStatus());
		assertSame(RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED,
				two.getStatus());
	}

	private List<RemoteRefUpdate> commands() throws IOException {
		List<RemoteRefUpdate> cmds = new ArrayList<>();
		cmds.add(new RemoteRefUpdate(null, null, obj1, "refs/heads/one",
				true /* force update */, null /* no local tracking ref */,
				ObjectId.zeroId()));
		cmds.add(new RemoteRefUpdate(null, null, obj2, "refs/heads/two",
				true /* force update */, null /* no local tracking ref */,
				obj1));
		return cmds;
