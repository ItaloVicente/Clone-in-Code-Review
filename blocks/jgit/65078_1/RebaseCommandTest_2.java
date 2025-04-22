			createBranch(a
			checkoutBranch("refs/heads/side");
			writeTrashFile("conflict"
			writeTrashFile(FILE1
			git.add().addFilepattern(".").call();
			RevCommit e = git.commit().setMessage("commit e").call();
