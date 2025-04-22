						if (wasWild) {
							return true;
						}
						if (previousWildmatch >= 0) {
							matcher = previousWildmatch + 1;
							right = previousBacktrackPos;
							wildmatchBacktrackPos = -1;
						} else {
							return false;
						}
