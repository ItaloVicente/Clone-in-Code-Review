
	@Test
	public void commandOrder() throws Exception {
		TestRepository<?> tr = new TestRepository<>(client);
		List<RemoteRefUpdate> updates = new ArrayList<>();
		for (int i = 9; i >= 0; i--) {
			String name = "refs/heads/b" + i;
			tr.branch(name).commit().create();
			RemoteRefUpdate rru = new RemoteRefUpdate(client
					ObjectId.zeroId());
			updates.add(rru);
		}

		PushResult result;
		try (Transport tn = testProtocol.open(uri
			result = tn.push(NullProgressMonitor.INSTANCE
		}

		for (RemoteRefUpdate remoteUpdate : result.getRemoteUpdates()) {
			assertEquals(
					"update should succeed on " + remoteUpdate.getRemoteName()
					RemoteRefUpdate.Status.OK
		}

		List<String> expected = remoteRefNames(updates);
		assertEquals(
				"ref names processed by ReceivePack should match input ref names in order"
				expected
		assertEquals(
				"remote ref names should match input ref names in order"
				expected
	}

	private static List<String> remoteRefNames(Collection<RemoteRefUpdate> updates) {
		List<String> result = new ArrayList<>();
		for (RemoteRefUpdate u : updates) {
			result.add(u.getRemoteName());
		}
		return result;
	}
