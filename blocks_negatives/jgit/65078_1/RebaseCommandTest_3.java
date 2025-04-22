		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());
		assertEquals(result.getConflicts().keySet(),
				Collections.singleton(FILE1));
		writeTrashFile(FILE1, "merge resolution");
		git.add().addFilepattern(FILE1).call();
		RevCommit d = git.commit().setMessage("commit d").call();
