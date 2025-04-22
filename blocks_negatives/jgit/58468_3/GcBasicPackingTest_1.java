		assertEquals(6, gc.getStatistics().numberOfBitmaps);
	}

	private void configureGcRange(GC myGc, int range) {
		PackConfig pconfig = new PackConfig(repo);
		pconfig.setBitmapCommitRange(range);
		myGc.setPackConfig(pconfig);
