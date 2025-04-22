		final InputStream is = new ByteArrayInputStream(os.toByteArray());
		final IndexPack indexer = new IndexPack(db, is, packBase);
		indexer.setKeepEmpty(true);
		indexer.setFixThin(thin);
		indexer.setIndexVersion(2);
		indexer.index(new TextProgressMonitor());
		pack = new PackFile(indexFile, packFile);
