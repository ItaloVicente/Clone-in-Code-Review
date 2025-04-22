	@Test
	public void shouldBeSymetric() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelBlob right = createGitModelBlob(zeroId(), getFile1Location());

		boolean actual1 = left.equals(right);
		boolean actual2 = right.equals(left);

		assertTrue(actual1 == actual2);
	}

	@Test
	public void shouldBeSymetric1() throws Exception {
		GitModelBlob left = createGitModelBlob(zeroId(), getFile1Location());
		GitModelCommit right = new GitModelCommit(createModelRepository(),
				getCommit(leftRepoFile, HEAD), LEFT);

		boolean actual1 = left.equals(right);
		boolean actual2 = right.equals(left);

		assertTrue(!actual1);
		assertTrue(!actual2);
	}

