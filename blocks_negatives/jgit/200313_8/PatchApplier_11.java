
				repo.getFS().setExecute(f,
						fileMode == FileMode.EXECUTABLE_FILE);
			}

			Instant lastModified = f == null ? null
					: repo.getFS().lastModifiedInstant(f);
			Attributes attributes = walk != null ? walk.getAttributes()
					: new Attributes();

			DirCacheEntry dce = insertToIndex(
					resultStreamLoader.supplier.load(),
					fh.getNewPath().getBytes(StandardCharsets.UTF_8), fileMode,
					lastModified, resultStreamLoader.length,
					attributes.get(Constants.ATTR_FILTER));
			dirCacheBuilder.add(dce);
			if (PatchType.GIT_BINARY.equals(fh.getPatchType())
					&& fh.getNewId() != null && fh.getNewId().isComplete()
					&& !fh.getNewId().toObjectId().equals(dce.getObjectId())) {
				throw new PatchApplyException(MessageFormat.format(
						JGitText.get().applyBinaryResultOidWrong,
						pathWithOriginalContent));
