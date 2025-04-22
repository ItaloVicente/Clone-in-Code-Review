		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c"
			git.add().addFilepattern("a").addFilepattern("b").call();
			Status stat = git.status().call();
			assertEquals(Sets.of("a"
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(Sets.of("c")
			git.commit().setMessage("initial").call();

			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("d"
			git.add().addFilepattern("a").addFilepattern("d").call();
			writeTrashFile("a"
			stat = git.status().call();
			assertEquals(Sets.of("d")
			assertEquals(Sets.of("a")
			assertEquals(0
			assertEquals(Sets.of("b"
			assertEquals(0
			assertEquals(Sets.of("c")
			git.add().addFilepattern(".").call();
			git.commit().setMessage("second").call();

			stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0

			deleteTrashFile("a");
			assertFalse(new File(git.getRepository().getWorkTree()
			git.add().addFilepattern("a").setUpdate(true).call();
			writeTrashFile("a"
			stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(Sets.of("a")
			assertEquals(Sets.of("a")
			git.commit().setMessage("t").call();

			writeTrashFile("sub/a"
			stat = git.status().call();
			assertEquals(1
			assertTrue(stat.getUntrackedFolders().contains("sub"));
		}
