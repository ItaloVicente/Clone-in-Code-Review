	@Test
	public void testModifyM2() throws Exception {
		ApplyResult result = init("M2", true, true);
		assertEquals(1, result.getUpdatedFiles().size());
		if (FS.DETECTED.supportsExecute()) {
			assertTrue(FS.DETECTED.canExecute(result.getUpdatedFiles().get(0)));
		}
		checkFile(new File(db.getWorkTree(), "M2"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testModifyM3() throws Exception {
		ApplyResult result = init("M3", true, true);
		assertEquals(1, result.getUpdatedFiles().size());
		if (FS.DETECTED.supportsExecute()) {
			assertFalse(
					FS.DETECTED.canExecute(result.getUpdatedFiles().get(0)));
		}
		checkFile(new File(db.getWorkTree(), "M3"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testModifyX() throws Exception {
		ApplyResult result = init("X");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "X"), result.getUpdatedFiles()
				.get(0));
		checkFile(new File(db.getWorkTree(), "X"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testModifyY() throws Exception {
		ApplyResult result = init("Y");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "Y"), result.getUpdatedFiles()
				.get(0));
		checkFile(new File(db.getWorkTree(), "Y"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testModifyZ() throws Exception {
		ApplyResult result = init("Z");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "Z"), result.getUpdatedFiles()
				.get(0));
		checkFile(new File(db.getWorkTree(), "Z"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testModifyNL1() throws Exception {
		ApplyResult result = init("NL1");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "NL1"), result
				.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "NL1"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testNonASCII() throws Exception {
		ApplyResult result = init("NonASCII");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "NonASCII"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "NonASCII"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testNonASCII2() throws Exception {
		ApplyResult result = init("NonASCII2");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "NonASCII2"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "NonASCII2"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testNonASCIIAdd() throws Exception {
		ApplyResult result = init("NonASCIIAdd");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "NonASCIIAdd"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "NonASCIIAdd"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testNonASCIIAdd2() throws Exception {
		ApplyResult result = init("NonASCIIAdd2", false, true);
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "NonASCIIAdd2"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "NonASCIIAdd2"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testNonASCIIDel() throws Exception {
		ApplyResult result = init("NonASCIIDel", true, false);
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "NonASCIIDel"),
				result.getUpdatedFiles().get(0));
		assertFalse(new File(db.getWorkTree(), "NonASCIIDel").exists());
	}

	@Test
	public void testRenameNoHunks() throws Exception {
		ApplyResult result = init("RenameNoHunks", true, true);
		assertEquals(2, result.getUpdatedFiles().size());
		assertTrue(result.getUpdatedFiles().contains(new File(db.getWorkTree(), "RenameNoHunks")));
		assertTrue(result.getUpdatedFiles().contains(new File(db.getWorkTree(), "nested/subdir/Renamed")));
		checkFile(new File(db.getWorkTree(), "nested/subdir/Renamed"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testRenameWithHunks() throws Exception {
		ApplyResult result = init("RenameWithHunks", true, true);
		assertEquals(2, result.getUpdatedFiles().size());
		assertTrue(result.getUpdatedFiles().contains(new File(db.getWorkTree(), "RenameWithHunks")));
		assertTrue(result.getUpdatedFiles().contains(new File(db.getWorkTree(), "nested/subdir/Renamed")));
		checkFile(new File(db.getWorkTree(), "nested/subdir/Renamed"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testCopyWithHunks() throws Exception {
		ApplyResult result = init("CopyWithHunks", true, true);
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "CopyResult"), result.getUpdatedFiles()
				.get(0));
		checkFile(new File(db.getWorkTree(), "CopyResult"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testShiftUp() throws Exception {
		ApplyResult result = init("ShiftUp");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "ShiftUp"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "ShiftUp"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testShiftUp2() throws Exception {
		ApplyResult result = init("ShiftUp2");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "ShiftUp2"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "ShiftUp2"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testShiftDown() throws Exception {
		ApplyResult result = init("ShiftDown");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "ShiftDown"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "ShiftDown"),
				b.getString(0, b.size(), false));
	}

	@Test
	public void testShiftDown2() throws Exception {
		ApplyResult result = init("ShiftDown2");
		assertEquals(1, result.getUpdatedFiles().size());
		assertEquals(new File(db.getWorkTree(), "ShiftDown2"),
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree(), "ShiftDown2"),
				b.getString(0, b.size(), false));
	}

