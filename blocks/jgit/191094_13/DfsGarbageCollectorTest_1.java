	private void gcWithObjectSizeIndex(int threshold) throws IOException {
		DfsGarbageCollector gc = new DfsGarbageCollector(repo);
		gc.getPackConfig().setMinBytesForObjSizeIndex(threshold);
		run(gc);
	}

