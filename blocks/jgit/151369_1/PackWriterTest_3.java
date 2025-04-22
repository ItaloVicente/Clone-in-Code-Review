			try (PackWriter pw = new PackWriter(repo)) {
				NullProgressMonitor m = NullProgressMonitor.INSTANCE;
				pw.preparePack(blobs.iterator());
				pw.writePack(m
				PackStatistics stats = pw.getStatistics();
				assertEquals(1
				assertTrue("Delta bytes not set."
						stats.byObjectType(OBJ_BLOB).getDeltaBytes() > 0);
			}
