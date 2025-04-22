		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
			assertEquals(
					"[a.txt
					"[b.txt
					indexState(CONTENT));
		}
