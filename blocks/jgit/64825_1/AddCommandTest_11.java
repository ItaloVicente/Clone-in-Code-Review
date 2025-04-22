		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub").call();
			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					indexState(CONTENT));
		}
