		} catch (IOException e) {
			if(!e.getMessage().equals(STALE_FILE_HANDLE_MSG)) {
				throw e;
			}
		}
		unpackedObjectCache().remove(id);
		return null;
	}

	protected ObjectLoader getObjectLoader(WindowCursor curs
		try (FileInputStream in = new FileInputStream(path)) {
			unpackedObjectCache().add(id);
			return UnpackedObject.open(in
