		git.add().addFilepattern("sub").call();
		assertEquals(
				"[sub/a.txt, mode:100644, content:modified content]" +
				"[sub/b.txt, mode:100644, content:content b]" +
				"[sub/c.txt, mode:100644, content:content c]",
				indexState(CONTENT));
