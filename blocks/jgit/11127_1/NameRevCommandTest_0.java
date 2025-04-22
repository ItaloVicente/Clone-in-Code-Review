	@Test
	public void ref() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag"
		assertOneResult("master"
				git.nameRev().addRef(db.getRef("refs/heads/master"))
		assertOneResult("tag"
				git.nameRev().addRef(db.getRef("refs/tags/tag"))
	}

	@Test
	public void annotatedTags() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag1"
		tr.update("refs/tags/tag2"
		assertOneResult("tag2"
	}

