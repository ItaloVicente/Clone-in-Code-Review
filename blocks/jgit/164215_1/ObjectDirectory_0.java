	private InsertLooseObjectResult tryMove(File tmp
			ObjectId id)
			throws IOException {
		Files.move(FileUtils.toPath(tmp)
				StandardCopyOption.ATOMIC_MOVE);
		dst.setReadOnly();
		unpackedObjectCache.add(id);
		return InsertLooseObjectResult.INSERTED;
	}

