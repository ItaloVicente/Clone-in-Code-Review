		try {
			File path = fileFor(id);
			FileInputStream in = new FileInputStream(path);
			try {
				unpackedObjectCache.add(id);
				return UnpackedObject.open(in, path, id, curs);
			} finally {
				in.close();
			}
