
		ObjectDirectoryPackParser p = (ObjectDirectoryPackParser) index(packData);
		p.setKeepEmpty(true);
		p.setAllowThin(thin);
		p.setIndexVersion(2);
		p.parse(NullProgressMonitor.INSTANCE);
		pack = p.getPackFile();
		assertNotNull("have PackFile after parsing"
	}

	private PackParser index(final byte[] packData) throws IOException {
		if (inserter == null)
			inserter = dst.newObjectInserter();
		return inserter.newPackParser(new ByteArrayInputStream(packData));
