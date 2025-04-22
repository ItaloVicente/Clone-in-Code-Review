	long getObjectSize1(final WindowCursor curs
			throws IOException {
		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (final PackFile p : pList.packs) {
				try {
					long sz = p.getObjectSize(curs
					if (0 <= sz)
						return sz;
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					removePack(p);
				}
			}
			return -1;
		}
	}

	@Override
	long getObjectSize2(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		try {
			File path = fileFor(objectName);
			FileInputStream in = new FileInputStream(path);
			try {
				return UnpackedObject.getSize(in
			} finally {
				in.close();
			}
		} catch (FileNotFoundException noFile) {
			return -1;
		}
	}

