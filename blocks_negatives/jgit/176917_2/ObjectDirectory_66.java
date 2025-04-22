	private long getPackedObjectSize(WindowCursor curs, AnyObjectId id) {
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						long len = p.getObjectSize(curs, id);
						p.resetTransientErrorCount();
						if (0 <= len)
							return len;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList))
							continue SEARCH;
					} catch (IOException e) {
						handlePackError(e, p);
					}
				}
				break SEARCH;
			}
		} while (searchPacksAgain(pList));
		return -1;
	}

	private long getLooseObjectSize(WindowCursor curs, AnyObjectId id)
			throws IOException {
		File f = fileFor(id);
		try (FileInputStream in = new FileInputStream(f)) {
			unpackedObjectCache.add(id);
			return UnpackedObject.getSize(in, id, curs);
		} catch (FileNotFoundException noFile) {
			if (f.exists()) {
				throw noFile;
			}
			unpackedObjectCache.remove(id);
			return -1;
		}
	}

