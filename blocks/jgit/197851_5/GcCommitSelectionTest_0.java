	@Test
	public void testBitmapDoesNotIncludeAnnotatedTags() throws Exception {
		String mainBranch = "refs/heads/main";
		BranchBuilder bb = tr.branch(mainBranch);

		String commitMsg = "commit msg";
		String fileBody = "file body";
		String tagName = "tag1";
		RevCommit commit0 = bb.commit().message(commitMsg + " 1").add("file1"
		RevCommit commit1 = bb.commit().message(commitMsg + " 2").add("file2"
		RevCommit commit2 = bb.commit().message(commitMsg + " 3").add("file3"
		tr.lightweightTag(tagName
		tr.branch(mainBranch).update(commit1);

		gc.setExpireAgeMillis(0);
		gc.gc();

		assertEquals(2
	}

