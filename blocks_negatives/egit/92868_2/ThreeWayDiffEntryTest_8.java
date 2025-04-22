		Git git = new Git(db);
		git.add().addFilepattern("a.txt").call();
		RevCommit c = git.commit().setMessage("initial commit").call();
		writeTrashFile("a.txt", "new line");
		RevCommit c1 = git.commit().setAll(true).setMessage("second commit")
				.call();
