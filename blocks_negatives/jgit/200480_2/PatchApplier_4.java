							FileUtils.rename(src, dest,
									StandardCopyOption.ATOMIC_MOVE);
						} catch (IOException e) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().renameFileFailed, src, dest),
									e);
