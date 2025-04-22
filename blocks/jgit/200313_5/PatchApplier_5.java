	public Result applyPatch(Patch p) throws IOException {
		Result result = new Result();
		DirCache dirCache = inCore() ? DirCache.read(reader
				: repo.lockDirCache();

		DirCacheBuilder dirCacheBuilder = dirCache.builder();
		Set<String> modifiedPaths = new HashSet<>();
		for (FileHeader fh : p.getFiles()) {
			ChangeType type = fh.getChangeType();
			switch (type) {
			case ADD: {
				File f = getFile(fh.getNewPath());
				if (f != null) {
					FileUtils.mkdirs(f.getParentFile()
					FileUtils.createNewFile(f);
