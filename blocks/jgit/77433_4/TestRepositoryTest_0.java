	@Test
	public void reattachToMaster_Race() throws Exception {
		RevCommit commit = tr.branch("master").commit().create();
		tr.branch("master").update(commit);
		tr.branch("other").update(commit);
		repo.updateRef("HEAD").link("refs/heads/master");

		tr.reset(commit);
		Ref head = repo.exactRef("HEAD");
		assertEquals(commit
		assertFalse(head.isSymbolic());

		RefUpdate refUpdate = repo.updateRef("HEAD");

		repo.updateRef("HEAD").link("refs/heads/other");

		assertEquals(
				RefUpdate.Result.LOCK_FAILURE
	}

	@Test
	public void nonRacingChange() throws Exception {
		tr.branch("master").update(tr.branch("master").commit().create());
		tr.branch("other").update(tr.branch("other").commit().create());
		repo.updateRef("HEAD").link("refs/heads/master");

		RefUpdate refUpdate = repo.updateRef("HEAD");

		tr.branch("master").update(tr.branch("master").commit().create());

		assertEquals(RefUpdate.Result.FORCED
	}

