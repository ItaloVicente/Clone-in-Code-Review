		for (int i = 0; i < 5; i++) {
			long start = System.currentTimeMillis();
			File f1 = createFile("newfile");
			FileSnapshot save = FileSnapshot.save(f1);
			long res = FS.getFsTimerResolution(f1.toPath()).toMillis();
			long end = System.currentTimeMillis();
			if (end - start > 2 * res) {
				continue;
			}
			assertTrue(save.isModified(f1));
			return;
		}
		fail("too much load for this test");
