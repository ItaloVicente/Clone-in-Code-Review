		} catch (IOException e) {
			if(!FileUtils.isStaleFileHandleInCausalChain(e)) {
				throw e;
			}
			LOG.debug("Suppressed stale file handle exception"
		}
		unpackedObjectCache().remove(id);
		return null;
	}

	ObjectLoader getObjectLoader(WindowCursor curs
		try (FileInputStream in = new FileInputStream(path)) {
			unpackedObjectCache().add(id);
			return UnpackedObject.open(in
