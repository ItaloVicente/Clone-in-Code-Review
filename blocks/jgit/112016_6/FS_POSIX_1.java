
	@Override
	public boolean supportsAtomicCreateNewFile() {
		if (supportsAtomicCreateNewFile == null) {
			determineAtomicFileCreationSupport();
		}
		return supportsAtomicCreateNewFile.booleanValue();
	}

	@SuppressWarnings("boxing")
	public boolean createNewFile(File lock) throws IOException {
		if (!lock.createNewFile()) {
			return false;
		}
		if (supportsAtomicCreateNewFile() || !supportsUnixNLink) {
			return true;
		}
		Path lockPath = lock.toPath();
		Path link = Files.createLink(Paths.get(lock.getAbsolutePath() + ".lnk")
				lockPath);
		try {
			Integer nlink = (Integer) (Files.getAttribute(lockPath
			if (nlink != 2) {
				LOG.warn("nlink of link to lock file {0} was not 2 but {1}"
						lock.getPath()
				return false;
			}
			return true;
		} catch (UnsupportedOperationException | IllegalArgumentException e) {
			supportsUnixNLink = false;
			return true;
		} finally {
			Files.delete(link);
		}
	}
