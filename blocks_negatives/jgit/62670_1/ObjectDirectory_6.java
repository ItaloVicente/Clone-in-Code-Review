		try {
			FileInputStream in = new FileInputStream(fileFor(id));
			try {
				unpackedObjectCache.add(id);
				return UnpackedObject.getSize(in, id, curs);
			} finally {
				in.close();
			}
