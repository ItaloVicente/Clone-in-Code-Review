	@Test
	public void testTreewalkEnterSubtree() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("b/c"
			writeTrashFile("z/.git"
			git.add().addFilepattern(".").call();
			git.rm().addFilepattern("a
					.addFilepattern("a0b").call();
			assertEquals("[a/b
					indexState(0));
			FileUtils.delete(new File(db.getWorkTree()
					FileUtils.RECURSIVE);

			TreeWalk tw = new TreeWalk(db);
			tw.addTree(new DirCacheIterator(db.readDirCache()));
			tw.addTree(new FileTreeIterator(db));
			assertTrue(tw.next());
			assertEquals("a"
			tw.enterSubtree();
			tw.next();
			assertEquals("a/b"
			tw.next();
			assertEquals("b"
			tw.enterSubtree();
			tw.next();
			assertEquals("b/c"
			assertNotNull(tw.getTree(0
			assertNotNull(tw.getTree(EmptyTreeIterator.class));
		}
	}

