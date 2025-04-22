	@Test
	public void TestBasicFilter() {
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(new String[] {});
		PatternFilter filter = new PatternFilter();

		filter.setPattern("b");
		assertTrue(filter.select(viewer, null, "a b c"));

		filter.setPattern("jre");
		assertTrue(filter.select(viewer, null,
				"Java build path buildpath problem exclusion inclusion pattern folder outputfolder filtered resource output compiler 1.5 5.0 J2SE5 project specific projectspecific strictly compatible JRE execution environment"));
	}

