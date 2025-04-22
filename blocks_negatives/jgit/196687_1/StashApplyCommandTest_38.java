		assertTrue(PATH + " should exist", check(PATH));
		assertEquals(PATH + " should have new content", "changed", read(PATH));
		assertTrue(path + " should exist", check(path));
		assertEquals(path + " should have new content", "untracked",
				read(path));
		recorder.assertEvent(new String[] { PATH, path }, ChangeRecorder.EMPTY);
