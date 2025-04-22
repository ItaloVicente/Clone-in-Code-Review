			git.add().addFilepattern("sub").call();
			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					"[sub/c.txt
					indexState(CONTENT));
		}
