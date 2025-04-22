		assertEquals("Unexpected modifications reported",
				Arrays.toString(expectedModified),
				Arrays.toString(actuallyModified));
		assertEquals("Unexpected deletions reported",
				Arrays.toString(expectedDeleted),
				Arrays.toString(actuallyDeleted));
