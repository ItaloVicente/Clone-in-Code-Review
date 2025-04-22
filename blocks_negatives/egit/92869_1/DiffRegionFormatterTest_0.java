		DiffRegionFormatter formatter = new DiffRegionFormatter(
				document);
		formatter.setRepository(repository);
		formatter.format(commit.getTree(), commit.getParent(0).getTree());
		assertTrue(document.getLength() > 0);
		DiffRegion[] regions = formatter.getRegions();
