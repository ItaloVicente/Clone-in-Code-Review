			RevCommit c1 = git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
			writeTrashFile("src/a.txt"
			fsTick(null);
			git.add().addFilepattern("src/a.txt").call();
			git.commit().setMessage("c2").call();
			assertTrue(git.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("foobar\n"
			git.checkout().setName(c1.getName()).call();
