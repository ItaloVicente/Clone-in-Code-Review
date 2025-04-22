				if (!allowConflicts) {
					throw new PatchApplyException(MessageFormat
							.format(JGitText.get().patchApplyException
				} else {

					newLines.add(applyAt++
					applyAt += hh.getOldImage().lineCount;
					newLines.add(applyAt++

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
								newLines.add(applyAt++
								lastWasRemoval = false;
								break;
							case '-':
								break;
							case '\\':
								if (!lastWasRemoval && isNoNewlineAtEnd(hunkLine)) {
									noNewLineAtEndOfNew = true;
								}
								break;
							default:
								break;
						}
					}
					newLines.add(applyAt++
