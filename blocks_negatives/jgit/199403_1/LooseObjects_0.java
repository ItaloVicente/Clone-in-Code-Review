		} catch (FileNotFoundException noFile) {
			if (f.exists()) {
				throw noFile;
			}
			unpackedObjectCache().remove(id);
			return -1;
