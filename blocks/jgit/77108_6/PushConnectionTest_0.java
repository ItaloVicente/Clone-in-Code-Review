
	@Test
	public void limitCommandBytes() throws IOException {
		Map<String
		for (int i = 0; i < 4; i++) {
			RemoteRefUpdate rru = new RemoteRefUpdate(
					null
					false
			updates.put(rru.getRemoteName()
		}

		server.getConfig().setInt("receive"
		try (Transport tn = testProtocol.open(uri
				PushConnection connection = tn.openPush()) {
			try {
				connection.push(NullProgressMonitor.INSTANCE
				fail("server did not abort");
			} catch (TransportException e) {
				String msg = e.getMessage();
				assertEquals("remote: Too many commands"
			}
		}
	}
