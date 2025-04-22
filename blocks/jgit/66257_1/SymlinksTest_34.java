		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit base = git.commit().setMessage("init").call();
			writeTrashFile("target"
			FileUtils.createSymLink(new File(db.getWorkTree()
			git.add().addFilepattern("target").addFilepattern("link").call();
			git.commit().setMessage("add target").call();
			assertEquals(4
			git.checkout().setName(base.name()).call();
			assertEquals(2
			git.checkout().setName("master").call();
			assertEquals(4
			String data = read(new File(db.getWorkTree()
			assertEquals(8
			assertEquals("someData"
			data = read(new File(db.getWorkTree()
			assertEquals("target"
					FileUtils.readSymLink(new File(db.getWorkTree()
			assertEquals("someData"
		}
