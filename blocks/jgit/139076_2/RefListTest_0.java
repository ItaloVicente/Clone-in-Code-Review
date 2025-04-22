	@Test
	public void testBuilder_AddThenDedupe() {
		RefList.Builder<Ref> builder = new RefList.Builder<>(1);
		builder.add(REF_B);
		builder.add(REF_A);
		builder.add(REF_A);
		builder.add(REF_B);
		builder.add(REF_c);

		builder.sort();
		builder.dedupe((a
		RefList<Ref> list = builder.toRefList();

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c
	}

	@Test
	public void testBuilder_AddThenDedupe_Border() {
		RefList.Builder<Ref> builder = new RefList.Builder<>(1);
		builder.sort();
		builder.dedupe((a
		RefList<Ref> list = builder.toRefList();
		assertTrue(list.isEmpty());

		builder = new RefList.Builder<>(1);
		builder.add(REF_A);
		builder.sort();
		builder.dedupe((a
		list = builder.toRefList();
		assertEquals(1
	}

