				if (!allowConflicts) {
					throw new PatchApplyException(MessageFormat
							.format(JGitText.get().patchApplyException
				} else {
					result.pathsWithConflicts.add(fh.getNewPath());
					newLines.add(Math.min(applyAt++
					applyAt += hh.getOldImage().lineCount;
					newLines.add(Math.min(applyAt++

					int sz = hunkLines.size();
					for (int j = 1; j < sz; j++) {
						ByteBuffer hunkLine = hunkLines.get(j);
						if (!hunkLine.hasRemaining()) {
							applyAt++;
							lastWasRemoval = false;
							continue;
						}
						switch (hunkLine.array()[hunkLine.position()]) {
							case ' ':
							case '+':
								newLines.add(Math.min(applyAt++
								break;
							case '-':
							case '\\':
							default:
								break;
						}
					}
					newLines.add(Math.min(applyAt++
