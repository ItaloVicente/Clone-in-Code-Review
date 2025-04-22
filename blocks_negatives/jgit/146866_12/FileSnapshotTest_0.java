		Assume.assumeTrue(
				fsAttrCache.getFsTimestampResolution()
						.compareTo(Duration.ofMillis(10)) > 0);
		Path f1 = createFile("newfile");
		FileSnapshot save = FileSnapshot.save(f1.toFile());
		assertTrue(save.isModified(f1.toFile()));
