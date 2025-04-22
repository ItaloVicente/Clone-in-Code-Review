	private void removeOldPack(File packFile
			int deleteOptions) throws IOException {
		if (pconfig != null && pconfig.isPreserveOldPacks()) {
			File oldPackDir = repo.getObjectDatabase().getPreservedDirectory();
			FileUtils.mkdir(oldPackDir

			File oldPackFile = new File(oldPackDir
			FileUtils.rename(packFile
		} else {
			FileUtils.delete(packFile
		}
	}

	private void prunePreserved() {
		if (pconfig != null && pconfig.isPrunePreserved()) {
			try {
				FileUtils.delete(repo.getObjectDatabase().getPreservedDirectory()
						FileUtils.RECURSIVE | FileUtils.RETRY | FileUtils.SKIP_MISSING);
			} catch (IOException e) {
			}
		}
	}

