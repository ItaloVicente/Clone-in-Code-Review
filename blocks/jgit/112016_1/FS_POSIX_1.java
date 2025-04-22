
	private boolean supportsUnixView = true;

	@SuppressWarnings("boxing")
	public boolean createNewFile(File lock) throws IOException {
		if (!lock.createNewFile()) {
			return false;
		}
		if (supportsAtomicCreateNewFile() || !supportsUnixView) {
			return true;
		}
		Path lockPath = lock.toPath();
		Path link = Files.createLink(Paths.get(lock.getAbsolutePath() + ".lnk")
				lockPath);
		try {
			Integer nlink = (Integer) (Files.getAttribute(lockPath
			if (nlink == 2) {
				return true;
			} else {
				LOG.warn("nlink of link to lock file {0} was not 2 but {1}"
						lock.getPath()
			}
		} catch (UnsupportedOperationException e) {
			supportsUnixView = false;
			return true;
		} finally {
			Files.delete(link);
		}
	}
