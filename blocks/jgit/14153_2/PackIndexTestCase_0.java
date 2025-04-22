	@Test
	public void testCompareEntriesOffsetsWithGetOffsets() {
		int i = 0;
		for (MutableEntry me : smallIdx) {
			assertEquals(smallIdx.getOffset(i++)
		}
		int j = 0;
		for (MutableEntry me : denseIdx) {
			assertEquals(denseIdx.getOffset(j++)
		}
	}

