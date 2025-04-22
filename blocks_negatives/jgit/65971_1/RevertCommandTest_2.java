		Git git = new Git(db);

		writeTrashFile("a", "first\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add first").call();

		writeTrashFile("a", "first\nsecond\n");
		git.add().addFilepattern("a").call();
		RevCommit secondCommit = git.commit().setMessage("add second").call();

		writeTrashFile("a", "first\nsecond\nthird\n");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add third").call();
