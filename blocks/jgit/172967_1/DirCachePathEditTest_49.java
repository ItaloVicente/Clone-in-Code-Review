	@Test
	public void testPathEditWithStagesAndReset() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheBuilder builder = new DirCacheBuilder(dc
		builder.add(createEntry("a"
		builder.add(createEntry("a"
		builder.add(createEntry("a"
		builder.finish();

		DirCacheEditor editor = dc.editor();
		PathEdit edit = new PathEdit("a") {

			@Override
			public void apply(DirCacheEntry ent) {
				ent.setStage(DirCacheEntry.STAGE_0);
			}
		};
		editor.add(edit);
		editor.finish();

		assertEquals(1
		DirCacheEntry entry = dc.getEntry(0);
		assertEquals("a"
		assertEquals(DirCacheEntry.STAGE_0
	}

