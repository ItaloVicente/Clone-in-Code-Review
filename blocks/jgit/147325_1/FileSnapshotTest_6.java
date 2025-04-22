		Assume.assumeTrue(fsAttrCache.getFsTimestampResolution()
				.compareTo(Duration.ofMillis(10)) > 0);
		for (int i = 0; i < 50; i++) {
			Instant start = Instant.now();
			Path f1 = createFile("newfile");
			FileSnapshot save = FileSnapshot.save(f1.toFile());
			Duration res = FS.getFileStoreAttributes(f1)
					.getFsTimestampResolution();
			Instant end = Instant.now();
			if (Duration.between(start
					.compareTo(res.multipliedBy(2)) > 0) {
				continue;
			}
			assertTrue(save.isModified(f1.toFile()));
			return;
		}
		fail("too much load for this test");
