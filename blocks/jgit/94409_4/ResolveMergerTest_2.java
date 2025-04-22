	@Theory
	public void checkContentMergeConflict_noTree(MergeStrategy strategy)
			throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

		writeTrashFile("file"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified file on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("file"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified file on side").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ResolveMerger merger =
					(ResolveMerger) strategy.newMerger(ins
			boolean noProblems = merger.merge(masterCommit
			assertFalse(noProblems);
			assertEquals(Arrays.asList("file")

			MergeFormatter fmt = new MergeFormatter();
			merger.getMergeResults().get("file");
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				fmt.formatMerge(out
						"BASE"
				String expected = "<<<<<<< OURS\n"
						+ "1master\n"
						+ "=======\n"
						+ "1side\n"
						+ ">>>>>>> THEIRS\n"
						+ "2\n"
						+ "3";
				assertEquals(expected
			}
		}
	}

