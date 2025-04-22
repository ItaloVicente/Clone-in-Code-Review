					break;
				case MODIFY:
					apply(fh.getOldPath(), dirCache, dirCacheBuilder,
							getFile(fh.getOldPath()), fh);
					break;
				case DELETE:
					if (!inCore()) {
						File old = getFile(fh.getOldPath());
						if (!old.delete())
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().cannotDeleteFile, old));
					}
					break;
				case RENAME: {
					File src = getFile(fh.getOldPath());
					File dest = getFile(fh.getNewPath());

					if (!inCore()) {
						/*
						 * this is odd: we rename the file on the FS, but
						 * apply() will write a fresh stream anyway, which will
						 * overwrite if there were hunks in the patch.
						 */
						try {
							FileUtils.mkdirs(dest.getParentFile(), true);
							FileUtils.rename(src, dest,
									StandardCopyOption.ATOMIC_MOVE);
						} catch (IOException e) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().renameFileFailed, src, dest),
									e);
						}
					}
					String pathWithOriginalContent = inCore() ?
							fh.getOldPath() : fh.getNewPath();
					apply(pathWithOriginalContent, dirCache, dirCacheBuilder, dest, fh);
					break;
