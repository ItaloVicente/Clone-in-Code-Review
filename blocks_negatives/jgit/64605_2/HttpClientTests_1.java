		Transport t = Transport.open(dst, dumbAuthBasicURI);
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username, AppServer.password));
		try {
			t.openFetch();
		} finally {
			t.close();
