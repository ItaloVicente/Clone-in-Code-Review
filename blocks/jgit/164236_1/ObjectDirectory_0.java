			File parentDir = dst.getParentFile();
			if (!parentDir.exists()) {
				FileUtils.mkdirs(parentDir
			}
			Files.move(FileUtils.toPath(tmp)
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
