	@Test
	public void testAllowAnySha1InWantConfig() {
		server.getConfig().setBoolean("uploadpack"
				true);

		UploadPack uploadPack = new UploadPack(server);
		assertEquals(RequestPolicy.ANY
	}

	@Test
	public void testAllowReachableSha1InWantConfig() {
		server.getConfig().setBoolean("uploadpack"
				"allowreachablesha1inwant"

		UploadPack uploadPack = new UploadPack(server);
		assertEquals(RequestPolicy.REACHABLE_COMMIT
				uploadPack.getRequestPolicy());
	}

	@Test
	public void testAllowTipSha1InWantConfig() {
		server.getConfig().setBoolean("uploadpack"
				true);

		UploadPack uploadPack = new UploadPack(server);
		assertEquals(RequestPolicy.TIP
	}

	@Test
	public void testAllowReachableTipSha1InWantConfig() {
		server.getConfig().setBoolean("uploadpack"
				"allowreachablesha1inwant"
		server.getConfig().setBoolean("uploadpack"
				true);

		UploadPack uploadPack = new UploadPack(server);
		assertEquals(RequestPolicy.REACHABLE_COMMIT_TIP
				uploadPack.getRequestPolicy());
	}

