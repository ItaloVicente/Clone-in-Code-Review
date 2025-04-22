
	@Test
	public void testPathEditShouldBeCalledForEachStage() throws Exception {
		DirCache dc = DirCache.newInCore();
		DirCacheBuilder builder = new DirCacheBuilder(dc
		builder.add(createEntry("a"
		builder.add(createEntry("a"
		builder.add(createEntry("a"
		builder.finish();

		DirCacheEditor editor = dc.editor();
		RecordingEdit recorder = new RecordingEdit("a");
		editor.add(recorder);
		editor.finish();

		List<DirCacheEntry> entries = recorder.entries;
		assertEquals(3
		assertEquals(DirCacheEntry.STAGE_1
		assertEquals(DirCacheEntry.STAGE_2
		assertEquals(DirCacheEntry.STAGE_3
	}

	private static DirCacheEntry createEntry(String path
		DirCacheEntry entry = new DirCacheEntry(path
		entry.setFileMode(FileMode.REGULAR_FILE);
		return entry;
	}
