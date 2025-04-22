	private InsertLooseObjectResult tryMove(File tmp, File dst,
			ObjectId id)
			throws IOException {
		Files.move(FileUtils.toPath(tmp), FileUtils.toPath(dst),
				StandardCopyOption.ATOMIC_MOVE);
		dst.setReadOnly();
		unpackedObjectCache.add(id);
		return InsertLooseObjectResult.INSERTED;
	}

