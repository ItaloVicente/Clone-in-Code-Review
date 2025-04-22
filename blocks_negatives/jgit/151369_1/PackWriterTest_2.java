		}

		try (PackWriter pw = new PackWriter(repo)) {
			NullProgressMonitor m = NullProgressMonitor.INSTANCE;
			pw.preparePack(blobs.iterator());
			pw.writePack(m, m, os);
			PackStatistics stats = pw.getStatistics();
			assertEquals(1, stats.getTotalDeltas());
			assertTrue("Delta bytes not set.",
					stats.byObjectType(OBJ_BLOB).getDeltaBytes() > 0);
