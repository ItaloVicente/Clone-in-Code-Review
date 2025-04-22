	@Test
	public void testListBranchesWithContains() throws Exception {
		git.branchCreate().setName("foo").setStartPoint(secondCommit).call();

		List<Ref> refs = git.branchList().call();
		assertEquals(2

		List<Ref> refsContainingSecond = git.branchList()
				.setContains(secondCommit.name()).call();
		assertEquals(1
		assertEquals("refs/heads/foo"
	}

