	public void testRenameBranchCannotLockAFileHEADisToLockTmp()
			throws IOException {
		tryRenameWhenLocked("RENAMED-REF.." + Thread.currentThread().getId(),
				"refs/heads/b", "refs/heads/new/name", "refs/heads/new/name");
	}

