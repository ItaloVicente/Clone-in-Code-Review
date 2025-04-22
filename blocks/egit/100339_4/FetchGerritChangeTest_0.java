
	@Test
	public void testRefFromChange() {
		assertEquals("refs/changes/00/98000/2",
				Change.create(98000, 2).getRefName());
		assertEquals("refs/changes/01/98001/2",
				Change.create(98001, 2).getRefName());
		assertEquals("refs/changes/01/1/1", Change.create(1, 1).getRefName());
		assertEquals("refs/changes/10/65510/6",
				Change.create(65510, 6).getRefName());
	}
