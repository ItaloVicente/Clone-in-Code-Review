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
					newLines.add(applyAt++, slice(hunkLine, 1));
					lastWasRemoval = false;
					break;
				case '\\':
					if (!lastWasRemoval && isNoNewlineAtEnd(hunkLine)) {
						noNewLineAtEndOfNew = true;
