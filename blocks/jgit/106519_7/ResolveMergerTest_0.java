	@Theory
	public void checkContentMergeBinaries(MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);
		final int LINELEN = 72;

		byte[] binary = new byte[LINELEN * 2000];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte)((i % LINELEN) == 0 ? '\n' : 'x');
		}
		binary[LINELEN * 1000 + 1] = '\0';

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

        	int idx = LINELEN * 1200 + 1;
		byte save = binary[idx];
		binary[idx] = '@';
		writeTrashFile("file"
		binary[idx] = save;
		git.add().addFilepattern("file").call();
		RevCommit masterCommit = git.commit().setAll(true)
			.setMessage("modified file l 1200").call();

		git.checkout().setCreateBranch(true).setStartPoint(first).setName("side").call();
		binary[LINELEN * 1500 + 1] = '!';
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit sideCommit = git.commit().setAll(true)
			.setMessage("modified file l 1500").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ResolveMerger merger =
				(ResolveMerger) strategy.newMerger(ins
			boolean noProblems = merger.merge(masterCommit
			assertFalse(noProblems);
		}
	}

