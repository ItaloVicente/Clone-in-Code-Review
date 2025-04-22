	private List<RemoteRefUpdate> commands() throws IOException {
		List<RemoteRefUpdate> cmds = new ArrayList<>();
		cmds.add(new RemoteRefUpdate(null
				ObjectId.zeroId()));
		cmds.add(new RemoteRefUpdate(null
				obj1));
		return cmds;
	}

	@Test
	public void testNonAtomicPushWithOptions() throws Exception {
		PushResult r;
		server.setPerformsAtomicTransactions(false);
		List<String> pushOptions = Arrays.asList("Hello"

		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(false);
			tn.setPushOptions(pushOptions);

			Map<String
			for (RemoteRefUpdate rru : commands()) {
				updates.put(rru.getRemoteName()
			}

			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");

		assertSame(RemoteRefUpdate.Status.OK
		assertSame(RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED
				two.getStatus());
		assertEquals(pushOptions
	}

	@Test
