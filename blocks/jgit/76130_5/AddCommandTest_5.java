
			RevCommit c1 = git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
			f = writeTrashFile("src/a.txt"
			if (!doSmudge) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").call();
			git.commit().setMessage("c2").call();
			assertTrue(git.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("foobar\n"
			git.checkout().setName(c1.getName()).call();
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals(
					read("src/a.txt"));
