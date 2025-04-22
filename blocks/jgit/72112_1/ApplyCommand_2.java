					if (hh.getNewStartLine() == 0) {
						newLines.clear();
					} else {
						if (!newLines.get(hh.getNewStartLine() - 1 + pos)
								.equals(hunkLine.substring(1))) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().patchApplyException
						}
						newLines.remove(hh.getNewStartLine() - 1 + pos);
