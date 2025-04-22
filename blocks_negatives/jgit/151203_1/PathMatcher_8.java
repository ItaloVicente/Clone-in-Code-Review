						if (right == endExcl - 1) {
							return !dirOnly || assumeDirectory;
						}
						if (wasWild) {
							return true;
						}
						if (lastWildmatch >= 0) {
							matcher = lastWildmatch + 1;
							right = wildmatchBacktrackPos;
							wildmatchBacktrackPos = -1;
						} else {
							return false;
						}
