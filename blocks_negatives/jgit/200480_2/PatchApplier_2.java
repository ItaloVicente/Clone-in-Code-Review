				case DELETE:
					if (!inCore()) {
						File old = getFile(fh.getOldPath());
						if (!old.delete())
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().cannotDeleteFile, old));
