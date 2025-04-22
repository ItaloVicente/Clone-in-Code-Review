	@Test
	public void testAddAllFromOtherList() {
		BlockList<Integer> src = new BlockList<Integer>(4);
		int cnt = BlockList.BLOCK_SIZE * 2;

		for (int i = 0; i < cnt; i++)
			src.add(Integer.valueOf(42 + i));
		src.add(Integer.valueOf(1));

		BlockList<Integer> dst = new BlockList<Integer>(4);
		dst.add(Integer.valueOf(255));
		dst.addAll(src);
		assertEquals(cnt + 2
		for (int i = 0; i < cnt; i++)
			assertEquals(Integer.valueOf(42 + i)
		assertEquals(Integer.valueOf(1)
	}

