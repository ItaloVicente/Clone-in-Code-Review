		assertFileAddition(c, rightResult.get(0).getChildren().get("folder/a.txt"), "a.txt", RIGHT);
		assertFileAddition(c, rightResult.get(0).getChildren().get("folder2/b.txt"), "b.txt",RIGHT);
	}

	@Test
	public void shouldApplyPathFilter() throws Exception {
		Git git = new Git(db);
		writeTrashFile("folder/a.txt", "content");
		writeTrashFile("folder2/b.txt", "b content");
		git.add().addFilepattern("folder/a.txt").call();
		git.add().addFilepattern("folder2/b.txt").call();
		RevCommit c = commit(git, "first commit");

		PathFilter pathFilter = PathFilter.create("folder");
		List<Commit> leftResult = CheckedInCommitsCache.build(db, initialTagId(), c, pathFilter);

		assertThat(leftResult, notNullValue());
		assertThat(Integer.valueOf(leftResult.size()), is(Integer.valueOf(1)));
		assertThat(leftResult.get(0).getShortMessage(), is("first commit"));

		assertThat(leftResult.get(0).getChildren(), notNullValue());
		assertThat(leftResult.get(0).getChildren().size(), is(1));

		assertFileDeletion(c, leftResult.get(0).getChildren().get("folder/a.txt"), "a.txt", LEFT);
