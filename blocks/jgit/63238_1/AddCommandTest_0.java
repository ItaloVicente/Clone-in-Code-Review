	@Test
	public void fileReplacedByDirectoryIsFineButCreatesCorruptRepository()
			throws IOException
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		PrintWriter writer = new PrintWriter(file);
		writer.print("content");
		writer.close();

		Git git = new Git(db);
		git.add().addFilepattern("sub").call();
		assertEquals(
				"[sub
				indexState(CONTENT));

		FileUtils.delete(file);
		FileUtils.mkdir(file);
		file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		writer = new PrintWriter(file);
		writer.print("other");
		writer.close();
		git.add().addFilepattern("sub/a.txt").call();
		assertEquals(
				"[sub
				"[sub/a.txt
				indexState(CONTENT));

		RevCommit c = git.commit().setMessage("ok").call();
		try (ObjectReader or = db.newObjectReader()) {
			byte[] raw = or.open(c.getTree()).getCachedBytes();
			try {
				new ObjectChecker().checkTree(raw);
				fail("ObjectChecker should catch corruption");
			} catch (CorruptObjectException e) {
				assertEquals("duplicate entry names"
			}
		}
	}

