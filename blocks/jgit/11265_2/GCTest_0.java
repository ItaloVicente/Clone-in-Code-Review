	@Test
	public void testPackRepoWithCorruptReflog() throws Exception {
		RefUpdate ru = repo.updateRef(Constants.HEAD);
		ru.link("refs/to/garbage");
		tr.branch("refs/heads/master").commit().add("A"
				.create();
		Git.wrap(repo).checkout().setName("refs/heads/master").call();
		gc.gc();
	}

