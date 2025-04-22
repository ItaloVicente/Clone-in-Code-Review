		RevCommit c;
		RevCommit c1;
		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.txt").call();
			c = git.commit().setMessage("initial commit").call();
			writeTrashFile("a.txt", "new line");
			c1 = git.commit().setAll(true).setMessage("second commit").call();
		}
