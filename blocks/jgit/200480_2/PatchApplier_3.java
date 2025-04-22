					case MODIFY:
						apply(fh.getOldPath()
								getFile(fh.getOldPath())
						break;
					case DELETE:
						if (!inCore()) {
							File old = getFile(fh.getOldPath());
							if (!old.delete())
								throw new PatchApplyException(MessageFormat.format(
										JGitText.get().cannotDeleteFile
						}
						break;
					case RENAME: {
						File src = getFile(fh.getOldPath());
						File dest = getFile(fh.getNewPath());

						if (!inCore()) {
							try {
								FileUtils.mkdirs(dest.getParentFile()
								FileUtils.rename(src
										StandardCopyOption.ATOMIC_MOVE);
							} catch (IOException e) {
								throw new PatchApplyException(MessageFormat.format(
										JGitText.get().renameFileFailed
										e);
							}
						}
						String pathWithOriginalContent = inCore() ?
								fh.getOldPath() : fh.getNewPath();
						apply(pathWithOriginalContent
						break;
