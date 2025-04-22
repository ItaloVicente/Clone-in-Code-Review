
	@Override
	public LockToken createNewFileAtomic(File file) throws IOException {
		if (!file.createNewFile()) {
			return token(false
		}
		if (supportsAtomicCreateNewFile() || !supportsUnixNLink) {
			return token(true
		}
		Path link = null;
		Path path = file.toPath();
		try {
			link = Files.createLink(Paths.get(uniqueLinkPath(file))
			Integer nlink = (Integer) (Files.getAttribute(path
			if (nlink.intValue() > 2) {
				LOG.warn(MessageFormat.format(
						JGitText.get().failedAtomicFileCreation
				return token(false
			} else if (nlink.intValue() < 2) {
				supportsUnixNLink = false;
			}
			return token(true
		} catch (UnsupportedOperationException | IllegalArgumentException e) {
			supportsUnixNLink = false;
			return token(true
		}
	}

	private static LockToken token(boolean created
		return ((p != null) && Files.exists(p))
				? new LockToken(created
				: new LockToken(created
	}

	private static String uniqueLinkPath(File file) {
		UUID id = UUID.randomUUID();
				+ Long.toHexString(id.getMostSignificantBits())
				+ Long.toHexString(id.getLeastSignificantBits());
	}
