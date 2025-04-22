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
