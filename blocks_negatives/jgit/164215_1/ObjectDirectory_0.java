			Files.move(FileUtils.toPath(tmp), FileUtils.toPath(dst),
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage(), e);
			FileUtils.delete(tmp, FileUtils.RETRY);
			return InsertLooseObjectResult.FAILURE;
