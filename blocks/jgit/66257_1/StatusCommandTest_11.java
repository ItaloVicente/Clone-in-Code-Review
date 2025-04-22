		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("D/b"
			writeTrashFile("D/c"
			writeTrashFile("D/D/d"
			git.add().addFilepattern(".").call();

			writeTrashFile("a"
			writeTrashFile("D/b"
			writeTrashFile("D/D/d"


			Status stat = git.status().addPath("x").call();
			assertEquals(0

			stat = git.status().addPath("a").call();
			assertEquals(Sets.of("a")

			stat = git.status().addPath("D").call();
			assertEquals(Sets.of("D/b"

			stat = git.status().addPath("D/D").addPath("a").call();
			assertEquals(Sets.of("a"

			stat = git.status().call();
			assertEquals(Sets.of("a"
		}
