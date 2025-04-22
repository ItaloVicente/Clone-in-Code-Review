	@Override
	@After
	public void tearDown() throws Exception {
		ArchiveCommand.unregisterFormat("zip");
		ArchiveCommand.unregisterFormat("tar");
	}

