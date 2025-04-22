	private void prunePack(String packName) {
		PackExt[] extensions = PackExt.values();
		try {
			int deleteOptions = FileUtils.RETRY | FileUtils.SKIP_MISSING;
			for (PackExt ext : extensions)
				if (PackExt.PACK.equals(ext)) {
					File f = nameFor(packName
					FileUtils.delete(f
					break;
				}
			deleteOptions |= FileUtils.IGNORE_ERRORS;
			for (PackExt ext : extensions) {
				if (!PackExt.PACK.equals(ext)) {
					File f = nameFor(packName
					FileUtils.delete(f
				}
			}
		} catch (IOException e) {
		}
	}

