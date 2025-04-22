					if (hh.getNewStartLine() == 0) {
						newLines.clear();
					} else {
						if (!newLines.get(hh.getNewStartLine() - 1 + pos)
								.equals(hunkLine.substring(1))) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().patchApplyException, hh));
						}
						newLines.remove(hh.getNewStartLine() - 1 + pos);
					}
