		File path = fileFor(id);
		try (FileInputStream in = new FileInputStream(path)) {
			unpackedObjectCache.add(id);
			return UnpackedObject.open(in, path, id, curs);
		} catch (FileNotFoundException noFile) {
			if (path.exists()) {
				throw noFile;
			}
			unpackedObjectCache.remove(id);
			return null;
		}
