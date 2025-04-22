		Git git = new Git(db);
		git.add().addFilepattern("sub").call();
		assertEquals(
				"[sub/a.txt, mode:100644, content:content]" +
				"[sub/b.txt, mode:100644, content:content b]",
				indexState(CONTENT));
