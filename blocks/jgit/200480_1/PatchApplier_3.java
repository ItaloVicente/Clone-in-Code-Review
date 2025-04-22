			} else {
				lineNumberShift = applyAt - hh.getNewStartLine() + 1;
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
							applyAt++;
							lastWasRemoval = false;
							break;
						case '-':
							newLines.remove(applyAt);
							lastWasRemoval = true;
							break;
						case '+':
							newLines.add(applyAt++
							lastWasRemoval = false;
							break;
						case '\\':
							if (!lastWasRemoval && isNoNewlineAtEnd(hunkLine)) {
								noNewLineAtEndOfNew = true;
							}
							break;
						default:
							break;
