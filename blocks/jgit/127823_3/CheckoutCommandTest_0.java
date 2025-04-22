	@Test
	public void testCheckoutWithConflictForce() throws Exception {
		git.checkout().setName("test").call();
		assertThat(read("Test.txt")
		writeTrashFile("Test.txt"
		git.checkout().setName("master").setForce(true).call();
		assertThat(read("Test.txt")
	}

