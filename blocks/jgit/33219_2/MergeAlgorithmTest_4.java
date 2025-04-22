	@Test
	public void testDeleteVsModifyNoNewlineAtEnd() throws IOException {
		newlineAtEnd = false;
		testDeleteVsModify();
	}

