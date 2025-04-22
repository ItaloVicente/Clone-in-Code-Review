		try (Git git = new Git(db)) {
			writeTrashFile("x"
			git.add().addFilepattern("x").call();
			RevCommit id1 = git.commit().setMessage("c1").call();

			writeTrashFile("f/g"
			git.rm().addFilepattern("x").call();
			git.add().addFilepattern("f/g").call();
			git.commit().setMessage("c2").call();
			deleteTrashFile("f/g");
			deleteTrashFile("f");

			git.reset().setMode(ResetType.HARD).setRef(id1.getName()).call();
			assertIndex(mkmap("x"
		}
