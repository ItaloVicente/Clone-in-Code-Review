		try (Repository newdb = createBareRepository()) {
			try (ObjectInserter oi = newdb.newObjectInserter()) {
				final ObjectId treeId = oi.insert(new TreeFormatter());
				assertEquals("4b825dc642cb6eb9a060e54bf8d69288fbee4904"
						treeId.name());
			}

			final File o = new File(
					new File(new File(newdb.getDirectory()
							"4b")
					"825dc642cb6eb9a060e54bf8d69288fbee4904");
			assertTrue("Exists " + o
			assertTrue("Read-only " + o
