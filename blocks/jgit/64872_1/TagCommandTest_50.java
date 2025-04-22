		try (Git git = new Git(db)) {
			git.add().addFilepattern("*").call();
			git.commit().setMessage("initial commit").call();
			List<Ref> list = git.tagList().call();
			assertEquals(0
		}
