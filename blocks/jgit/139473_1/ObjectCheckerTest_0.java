			assertEquals("byte 0x" + h + " not allowed in Windows filename"
		}
	}


	@Test
	public void testRejectInvalidCharacter() {
		try {
			checkOneName("te/st");
			fail("incorrectly accepted with /");
		} catch (CorruptObjectException e) {

			assertEquals("name contains '/'"
