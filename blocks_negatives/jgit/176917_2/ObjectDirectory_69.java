
		final File dst = fileFor(id);
		if (dst.exists()) {
			FileUtils.delete(tmp, FileUtils.RETRY);
			return InsertLooseObjectResult.EXISTS_LOOSE;
		}

		try {
			return tryMove(tmp, dst, id);
		} catch (NoSuchFileException e) {
			FileUtils.mkdir(dst.getParentFile(), true);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			FileUtils.delete(tmp, FileUtils.RETRY);
			return InsertLooseObjectResult.FAILURE;
		}

		try {
			return tryMove(tmp, dst, id);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			FileUtils.delete(tmp, FileUtils.RETRY);
			return InsertLooseObjectResult.FAILURE;
		}
	}

	private InsertLooseObjectResult tryMove(File tmp, File dst,
			ObjectId id)
			throws IOException {
		Files.move(FileUtils.toPath(tmp), FileUtils.toPath(dst),
				StandardCopyOption.ATOMIC_MOVE);
		dst.setReadOnly();
		unpackedObjectCache.add(id);
		return InsertLooseObjectResult.INSERTED;
	}

	boolean searchPacksAgain(PackList old) {
		boolean trustFolderStat = config.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION,
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT, true);

		return ((!trustFolderStat) || old.snapshot.isModified(packDirectory))
				&& old != scanPacks(old);
