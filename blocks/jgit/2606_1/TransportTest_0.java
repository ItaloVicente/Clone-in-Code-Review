
	@Test
	public void testNonExistingLocalRepositoryException() throws IOException {
		String path = "/path/to/non-existing/repository.git";
		try {
			Transport.open(db
		} catch (Exception e) {
			assertTrue(e.getMessage().equals(path + " does not exist"));
		}
	}
