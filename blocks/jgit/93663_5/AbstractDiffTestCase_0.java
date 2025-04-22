	@Test
	public void testEdit_DeleteNearCommonTail() {
		EditList r = diff(t("aCq}nD}nb")
		assertEquals(new Edit(1
		assertEquals(new Edit(5
		assertEquals(2
	}

	@Test
	public void testEdit_DeleteNearCommonCenter() {
		EditList r = diff(t("abcd123123uvwxpq")
		assertEquals(new Edit(1
		assertEquals(new Edit(7
		assertEquals(new Edit(14
		assertEquals(3
	}

	@Test
	public void testEdit_InsertNearCommonCenter() {
		EditList r = diff(t("aBcd123uvwxPq")
		assertEquals(new Edit(1
		assertEquals(new Edit(7
		assertEquals(new Edit(11
		assertEquals(3
	}

