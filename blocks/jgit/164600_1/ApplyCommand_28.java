			}

			if (hh.getNewStartLine() == 0) {
				if (fh.getHunks().size() == 1
						&& canApplyAt(hunkLines
					newLines.clear();
					break;
				}
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException
			}
			int applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
			if (applyAt < afterLastHunk && lineNumberShift < 0) {
				applyAt = hh.getNewStartLine() - 1;
				lineNumberShift = 0;
			}
			if (applyAt < afterLastHunk) {
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException
			}
			boolean applies = false;
			int oldLinesInHunk = hh.getLinesContext()
					+ hh.getOldImage().getLinesDeleted();
			if (oldLinesInHunk <= 1) {
				applies = canApplyAt(hunkLines
				if (!applies && lineNumberShift != 0) {
					applyAt = hh.getNewStartLine() - 1;
					applies = applyAt >= afterLastHunk
							&& canApplyAt(hunkLines
				}
			} else {
				int maxShift = applyAt - afterLastHunk;
				for (int shift = 0; shift <= maxShift; shift++) {
					if (canApplyAt(hunkLines
						applies = true;
						applyAt -= shift;
						break;
					}
				}
				if (!applies) {
					applyAt = hh.getNewStartLine() - 1 + lineNumberShift;
					maxShift = newLines.size() - applyAt - oldLinesInHunk;
					for (int shift = 1; shift <= maxShift; shift++) {
						if (canApplyAt(hunkLines
							applies = true;
							applyAt += shift;
							break;
						}
					}
				}
			}
			if (!applies) {
				throw new PatchApplyException(MessageFormat
						.format(JGitText.get().patchApplyException
			}
			lineNumberShift = applyAt - hh.getNewStartLine() + 1;
			int sz = hunkLines.size();
			for (int j = 1; j < sz; j++) {
