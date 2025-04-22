	@Test
	public void testListRemote_Dumb_ClientCertAuth() throws Exception {
		Repository dst = createBareRepository();
		StoredConfig config = dst.getConfig();
		config.setBoolean("http"
		config.setString("http"
		config.setString("http"
		config.save();
		Transport t = Transport.open(dst
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username
		try {
			FetchConnection c = t.openFetch();
			try {
				Ref head = c.getRef(Constants.HEAD);
				assertNotNull(head);
				assertTrue(head
						.getObjectId()
						.equals(ObjectId
								.fromString("c58a4bec12cbf30cc1894f5ce8cf604bd6bad596")));
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}

		config = dst.getConfig();
		config.setBoolean("http"
		config.setString("http"
		config.setString("http"
		config.save();
		t = Transport.open(dst
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username
		try {
			FetchConnection c = t.openFetch();
			try {
				Ref head = c.getRef(Constants.HEAD);
				assertNotNull(head);
				assertTrue(head
						.getObjectId()
						.equals(ObjectId
								.fromString("c58a4bec12cbf30cc1894f5ce8cf604bd6bad596")));
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}
	}

	@Test
	public void testListRemote_Smart_PushWithClientCertAuth() throws Exception {
		Repository dst = createBareRepository();
		StoredConfig config = dst.getConfig();
		config.setBoolean("http"
		config.setString("http"
		config.setString("http"
		config.save();
		Transport t = Transport.open(dst
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username
		try {
			PushConnection c = t.openPush();
			try {
				Map<String
				assertNotNull(refs);
				assertTrue(refs.containsKey("refs/heads/master"));
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}

		config = dst.getConfig();
		config.setBoolean("http"
		config.setString("http"
		config.save();
		t = Transport.open(dst
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username
		try {
			PushConnection c = t.openPush();
			try {
				Map<String
				assertNotNull(refs);
				assertTrue(refs.containsKey("refs/heads/master"));
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}
	}

