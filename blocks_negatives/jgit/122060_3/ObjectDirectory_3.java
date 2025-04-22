		final File dst = fileFor(id);
		if (dst.exists()) {
			FileUtils.delete(tmp, FileUtils.RETRY);
			return InsertLooseObjectResult.EXISTS_LOOSE;
		}
		try {
			Files.move(FileUtils.toPath(tmp), FileUtils.toPath(dst),
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
		}

		FileUtils.mkdir(dst.getParentFile(), true);
		try {
			Files.move(FileUtils.toPath(tmp), FileUtils.toPath(dst),
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.debug(e.getMessage(), e);
