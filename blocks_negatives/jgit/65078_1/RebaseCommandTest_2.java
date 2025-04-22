		writeTrashFile("file2", "blah");
		git.add().addFilepattern("file2").call();
		RevCommit b = git.commit().setMessage("commit b").call();

		createBranch(b, "refs/heads/topic");
		checkoutBranch("refs/heads/topic");

		writeTrashFile("file3", "more changess");
		writeTrashFile(FILE1, "preparing conflict");
		git.add().addFilepattern("file3").addFilepattern(FILE1).call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(a, "refs/heads/side");
		checkoutBranch("refs/heads/side");
		writeTrashFile("conflict", "e");
		writeTrashFile(FILE1, FILE1 + "\n" + "line 2");
		git.add().addFilepattern(".").call();
		RevCommit e = git.commit().setMessage("commit e").call();
