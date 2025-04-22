					lastWasRemoval = false;
					break;
				case '\\':
					if (!lastWasRemoval && isNoNewlineAtEnd(hunkLine)) {
						noNewLineAtEndOfNew = true;
					}
