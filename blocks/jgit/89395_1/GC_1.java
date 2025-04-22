	private void deleteOrphans() {
		File[] files = new File(repo.getObjectsDirectory()
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isFile() && isOrphan(file)) {
				file.delete();
			}
		}
	}

	private boolean isOrphan(File file) {
		String fileName = file.getName();

				(fileName.endsWith(PackExt.BITMAP_INDEX.getExtension())
						|| fileName.endsWith(PackExt.INDEX.getExtension()))
				&& !new File(file.getParentFile()
						fileName.substring(0
								+ PackExt.PACK.getExtension()).exists();
	}

