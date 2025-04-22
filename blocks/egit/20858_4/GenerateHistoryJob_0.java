							boolean commitFound = false;
							for (RevCommit commit : loadedCommits) {
								if (commit.getId().equals(commitToShow.getId())) {
									commitFound = true;
									break;
								}
							}
							commitNotFound = !commitFound;
						} else {
