	@Test
	public void testScanningForPackfiles() throws Exception {
		ObjectId unknownID = ObjectId
				.fromString("c0ffee09d0b63d694bf49bc1e6847473f42d4a8c");
		GC gc = new GC(db);
		gc.setExpireAgeMillis(0);
		gc.setPackExpireAgeMillis(0);

		try (FileRepository receivingDB = new FileRepository(
				db.getDirectory())) {
			FileBasedConfig cfg = receivingDB.getConfig();
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
			cfg.save();

			ObjectId id = commitFile("file.txt"
			gc.gc();
			assertFalse(receivingDB.hasObject(unknownID));
			assertTrue(receivingDB.getObjectDatabase().hasPackedObject(id));

			File packsFolder = new File(receivingDB.getObjectsDirectory()
					"pack");
			File tmpFile = new File(packsFolder
			RevCommit id2 = commitFile("file.txt"
			fsTick(null);

			assertTrue(tmpFile.createNewFile());
			assertFalse(receivingDB.hasObject(unknownID));

			gc.gc();

			Thread.sleep(2600);

			File[] ret = packsFolder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir
					return name.endsWith(".pack");
				}
			});
			assertTrue(ret.length == 1);
			Assume.assumeTrue(tmpFile.lastModified() == ret[0].lastModified());

			assertFalse(receivingDB.hasObject(unknownID));
			assertTrue(receivingDB.hasObject(id2));
		}
	}

