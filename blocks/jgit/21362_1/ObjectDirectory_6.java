		} while (searchPacksAgain(pList));
		return null;
	}

	ObjectLoader openLooseObject(WindowCursor curs
			throws IOException {
		try {
			File path = fileFor(id);
			FileInputStream in = new FileInputStream(path);
			try {
				unpackedObjectCache.add(id);
				return UnpackedObject.open(in
			} finally {
				in.close();
			}
		} catch (FileNotFoundException noFile) {
			unpackedObjectCache.remove(id);
