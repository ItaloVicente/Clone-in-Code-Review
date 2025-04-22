	@Test
	public void resolveUnnamedCurrentBranchCommits() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("edit file").call();

		assertEquals(c2
		assertEquals(c1

		git.checkout().setCreateBranch(true).setName("newbranch")
				.setStartPoint(c1).call();

		assertEquals(c1
		try {
			assertEquals(c1
		} catch (RevisionSyntaxException e) {
			assertNotNull(e);
		}

		git.checkout().setName(c2.getName()).call();
		assertEquals(c2
		assertEquals(c1
		assertEquals(c2
	}

