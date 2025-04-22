	@Test
	public void testLocalTransportWithRelativePath() throws Exception {
		FileRepository other = createWorkRepository();
		String otherDir = other.getWorkTree().getName();

		RemoteConfig config = new RemoteConfig(db.getConfig()
		config.addURI(new URIish("../" + otherDir));

		transport = Transport.open(db
	}

