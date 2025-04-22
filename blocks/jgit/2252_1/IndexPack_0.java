		BufferedInputStream in = new BufferedInputStream(System.in);
		ObjectInserter inserter = db.newObjectInserter();
		try {
			PackParser p = inserter.newPackParser(in);
			p.setAllowThin(fixThin);
			if (indexVersion != -1 && p instanceof ObjectDirectoryPackParser) {
				ObjectDirectoryPackParser imp = (ObjectDirectoryPackParser) p;
				imp.setIndexVersion(indexVersion);
			}
			p.parse(new TextProgressMonitor());
			inserter.flush();
		} finally {
			inserter.release();
		}
