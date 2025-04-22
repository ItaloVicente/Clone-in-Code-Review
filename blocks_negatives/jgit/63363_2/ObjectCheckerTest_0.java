		try {
			checker.check(Constants.OBJ_BAD, new byte[0]);
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException e) {
			final String m = e.getMessage();
			assertEquals(MessageFormat.format(
					JGitText.get().corruptObjectInvalidType2,
					valueOf(Constants.OBJ_BAD)), m);
		}
