
	@Test
	public void commitAmendWithoutAuthorShouldSetOriginalAuthorAndAuthorTime()
			throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();

		final String authorName = "First Author";
		final String authorEmail = "author@example.org";
		final Date authorDate = new Date(1349621117000L);
		PersonIdent firstAuthor = new PersonIdent(authorName
				authorDate
		git.commit().setMessage("initial commit").setAuthor(firstAuthor).call();

		RevCommit amended = git.commit().setAmend(true)
				.setMessage("amend commit").call();

		PersonIdent amendedAuthor = amended.getAuthorIdent();
		assertEquals(authorName
		assertEquals(authorEmail
		assertEquals(authorDate.getTime()
	}

	@Test
	public void commitAmendWithAuthorShouldUseIt() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		git.commit().setMessage("initial commit").call();

		RevCommit amended = git.commit().setAmend(true)
				.setAuthor("New Author"
				.setMessage("amend commit").call();

		PersonIdent amendedAuthor = amended.getAuthorIdent();
		assertEquals("New Author"
		assertEquals("newauthor@example.org"
	}
