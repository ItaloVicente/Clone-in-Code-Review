		checkoutBranch("refs/heads/master");
		writeTrashFile("fileg", "fileg");
		if (testConflict)
			writeTrashFile("conflict", "g");
		git.add().addFilepattern(".").call();
		RevCommit g = git.commit().setMessage("commit g").call();
