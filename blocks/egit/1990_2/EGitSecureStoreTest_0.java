	@Test
	public void testClearCredentialsTwice() throws Exception {
		URIish uri = new URIish("http://testRepo.example.com/testrepo");
		UserPasswordCredentials credentials = new UserPasswordCredentials(
				"agitter", "letmein");
		store.putCredentials(uri, credentials);
		store.clearCredentials(uri);
		assertEquals(null, store.getCredentials(uri));
		store.clearCredentials(uri);
		assertEquals(null, store.getCredentials(uri));
	}

