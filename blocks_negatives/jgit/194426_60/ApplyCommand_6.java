		try {
			final Patch p = new Patch();
			try {
				p.parse(in);
			} finally {
				in.close();
			}
			if (!p.getErrors().isEmpty()) {
				throw new PatchFormatException(p.getErrors());
			}
			Repository repository = getRepository();
			DirCache cache = repository.readDirCache();
			for (FileHeader fh : p.getFiles()) {
				ChangeType type = fh.getChangeType();
				File f = null;
				switch (type) {
				case ADD:
					f = getFile(fh.getNewPath(), true);
					apply(repository, fh.getNewPath(), cache, f, fh);
					break;
				case MODIFY:
					f = getFile(fh.getOldPath(), false);
					apply(repository, fh.getOldPath(), cache, f, fh);
					break;
				case DELETE:
					f = getFile(fh.getOldPath(), false);
					if (!f.delete())
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().cannotDeleteFile, f));
					break;
				case RENAME:
					f = getFile(fh.getOldPath(), false);
					File dest = getFile(fh.getNewPath(), false);
					try {
						FileUtils.mkdirs(dest.getParentFile(), true);
						FileUtils.rename(f, dest,
								StandardCopyOption.ATOMIC_MOVE);
					} catch (IOException e) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().renameFileFailed, f, dest), e);
					}
					apply(repository, fh.getOldPath(), cache, dest, fh);
					r.addUpdatedFile(dest);
					break;
				case COPY:
					File src = getFile(fh.getOldPath(), false);
					f = getFile(fh.getNewPath(), false);
					FileUtils.mkdirs(f.getParentFile(), true);
					Files.copy(src.toPath(), f.toPath());
					apply(repository, fh.getOldPath(), cache, f, fh);
				}
				r.addUpdatedFile(f);
			}
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException, e.getMessage()), e);
