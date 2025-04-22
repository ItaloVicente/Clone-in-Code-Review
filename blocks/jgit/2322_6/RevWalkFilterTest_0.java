	@Test
	public void testFilter_ALL_w_grafts() throws Exception {
		final RevCommit _ = commit();
		final RevCommit a = commit(_);
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		File graftsFile = db.getGraftsFile();
		FileUtils.mkdir(graftsFile.getParentFile()
		FileWriter fileWriter = new FileWriter(graftsFile);
		fileWriter.write(c.getId().name() + " " + a.getId().name() + "\n");
		fileWriter.close();
		RevWalk w = new RevWalk(db);
		w.setRevFilter(RevFilter.ALL);
		w.markStart(new RevCommit(c.getId()));
		RevCommit newC = w.next();
		assertCommit(c
		assertEquals(0
		RevCommit newA = w.next();
		assertCommit(a
		assertFlag(RevWalk.GRAFTED
		assertNotFlag(RevWalk.REWRITE
		RevCommit new_ = w.next();
		assertCommit(_
		assertNotFlag(RevWalk.GRAFTED
		assertNull(w.next());
	}

	@Test
	public void testFilter_ALL_w_replace() throws Exception {
		final RevCommit _ = commit();
		final RevCommit a = commit(_);
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		RefUpdate updateRef = db.updateRef(Constants.R_REPLACE + b.getName());
		updateRef.setNewObjectId(a.getId());
		Result update = updateRef.update();
		assertEquals(Result.NEW

		RevWalk w = new RevWalk(db);
		w.setRevFilter(RevFilter.ALL);
		w.markStart(new RevCommit(c.getId()));
		RevCommit newC = w.next();
		assertCommit(c
		assertEquals(0
		RevCommit newA = w.next();
		assertCommit(a
		assertFlag(RevWalk.GRAFTED
		assertNotFlag(RevWalk.REWRITE
		RevCommit new_ = w.next();
		assertCommit(_
		assertNotFlag(RevWalk.GRAFTED
		assertNull(w.next());
	}

