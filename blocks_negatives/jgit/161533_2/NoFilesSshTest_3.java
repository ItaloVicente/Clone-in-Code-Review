		File newHostKey = new File(getTemporaryDirectory(), "newhostkey");
		copyTestResource("id_ed25519", newHostKey);
		server.addHostKey(newHostKey.toPath(), true);
		testServerKey = load(newHostKey.toPath()).getPublic();
		assertTrue(newHostKey.delete());
		testUserKey = load(privateKey1.getAbsoluteFile().toPath());
