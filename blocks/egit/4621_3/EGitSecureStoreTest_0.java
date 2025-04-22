	@Test
	public void testEnsureDefaultPortHttp() throws Exception {
		URIish uri = new URIish("http://testRepo.example.com/testrepo");
		UserPasswordCredentials credentials = new UserPasswordCredentials(
				"agitter", "letmein");
		store.putCredentials(uri, credentials);
		URIish uri2 = new URIish("http://testRepo.example.com:80/testrepo");
		assertEquals(credentials.getUser(), store.getCredentials(uri2).getUser());
		assertEquals(credentials.getPassword(), store.getCredentials(uri2).getPassword());
	}

	@Test
	public void testEnsureDefaultPortHttps() throws Exception {
		URIish uri = new URIish("https://testRepo.example.com/testrepo");
		UserPasswordCredentials credentials = new UserPasswordCredentials(
				"agitter", "letmein");
		store.putCredentials(uri, credentials);
		URIish uri2 = new URIish("https://testRepo.example.com:443/testrepo");
		assertEquals(credentials.getUser(), store.getCredentials(uri2).getUser());
		assertEquals(credentials.getPassword(), store.getCredentials(uri2).getPassword());
	}

	@Test
	public void testEnsureDefaultPortSftp() throws Exception {
		URIish uri = new URIish("sftp://testRepo.example.com/testrepo");
		UserPasswordCredentials credentials = new UserPasswordCredentials(
				"agitter", "letmein");
		store.putCredentials(uri, credentials);
		URIish uri2 = new URIish("sftp://testRepo.example.com:22/testrepo");
		assertEquals(credentials.getUser(), store.getCredentials(uri2).getUser());
		assertEquals(credentials.getPassword(), store.getCredentials(uri2).getPassword());
	}

	@Test
	public void testEnsureDefaultPortFtp() throws Exception {
		URIish uri = new URIish("ftp://testRepo.example.com/testrepo");
		UserPasswordCredentials credentials = new UserPasswordCredentials(
				"agitter", "letmein");
		store.putCredentials(uri, credentials);
		URIish uri2 = new URIish("ftp://testRepo.example.com:21/testrepo");
		assertEquals(credentials.getUser(), store.getCredentials(uri2).getUser());
		assertEquals(credentials.getPassword(), store.getCredentials(uri2).getPassword());
	}

	@Test
	public void testEnsureDefaultPortSsh() throws Exception {
		URIish uri = new URIish("ssh://agitter@testRepo.example.com/testrepo");
		UserPasswordCredentials credentials = new UserPasswordCredentials(
				"agitter", "letmein");
		store.putCredentials(uri, credentials);
		URIish uri2 = new URIish("ssh://testRepo.example.com:22/testrepo");
		assertEquals(credentials.getUser(), store.getCredentials(uri2).getUser());
		assertEquals(credentials.getPassword(), store.getCredentials(uri2).getPassword());
	}

