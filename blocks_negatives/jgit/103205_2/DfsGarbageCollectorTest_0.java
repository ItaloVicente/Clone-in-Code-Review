	@Test
	public void leavesNonGcReftablesIfNotConfigured() throws Exception {
		String master = "refs/heads/master";
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update(master, commit1);

		DfsPackDescription t1 = odb.newPack(INSERT);
		try (DfsOutputStream out = odb.writeFile(t1, REFTABLE)) {
			out.write("ignored".getBytes(StandardCharsets.UTF_8));
			t1.addFileExt(REFTABLE);
		}
		odb.commitPack(Collections.singleton(t1), null);

		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.setReftableConfig(null);
		run(gc);

		assertEquals(1, odb.getPacks().length);
		DfsPackFile pack = odb.getPacks()[0];
		DfsPackDescription desc = pack.getPackDescription();
		assertEquals(GC, desc.getPackSource());
		assertTrue("commit0 in pack", isObjectInPack(commit0, pack));
		assertTrue("commit1 in pack", isObjectInPack(commit1, pack));

		DfsReftable[] tables = odb.getReftables();
		assertEquals(1, tables.length);
		assertEquals(t1, tables[0].getPackDescription());
	}

