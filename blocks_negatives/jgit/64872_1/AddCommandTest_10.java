		Git git = new Git(db);
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		assertEquals(
				"[a.txt, mode:100644, content:content]" +
				"[b.txt, mode:100644, content:content b]",
				indexState(CONTENT));
