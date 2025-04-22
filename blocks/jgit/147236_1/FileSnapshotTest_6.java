		Assume.assumeTrue(
				fsAttrCache.getFsTimestampResolution()
						.compareTo(Duration.ofMillis(10)) > 0);
		Path f1 = createFile("newfile");
		waitNextTick(f1);
		FileSnapshot save = FileSnapshot.save(f1.toFile());
		TimeUnit.NANOSECONDS.sleep(
				fsAttrCache.getFsTimestampResolution().dividedBy(2).toNanos());
		assertTrue(save.isModified(f1.toFile()));
