					if (!repoChanged) {
						if (headChanged) {
							toShow = toRevCommit(walk, headId);
						} else if (fetchHeadChanged) {
							toShow = toRevCommit(walk, fetchHeadId);
						}
