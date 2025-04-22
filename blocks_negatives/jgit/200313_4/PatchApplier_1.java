		Patch p = new Patch();
		try (InputStream inStream = patchInput) {
			p.parse(inStream);

			if (!p.getErrors().isEmpty()) {
				throw new PatchFormatException(p.getErrors());
			}

			DirCache dirCache = inCore() ? DirCache.read(reader, beforeTree)
					: repo.lockDirCache();

			DirCacheBuilder dirCacheBuilder = dirCache.builder();
			Set<String> modifiedPaths = new HashSet<>();
			for (FileHeader fh : p.getFiles()) {
				ChangeType type = fh.getChangeType();
				switch (type) {
				case ADD: {
					File f = getFile(fh.getNewPath());
					if (f != null) {
						try {
							FileUtils.mkdirs(f.getParentFile(), true);
							FileUtils.createNewFile(f);
						} catch (IOException e) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().createNewFileFailed, f), e);
						}
					}
					apply(fh.getNewPath(), dirCache, dirCacheBuilder, f, fh);
