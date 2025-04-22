		assertEquals(new Edit(4
		assertEquals(2
	}

	@Test
	public void testEdit_LinuxBug() {
		EditList r = diff(t("a{bcdE}z")
		assertEquals(new Edit(2
		assertEquals(new Edit(6
