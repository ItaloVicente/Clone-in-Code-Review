							boolean commitFound = false;
							for (RevCommit commit : loadedCommits) {
								if (commit.getId() == commitToShow.getId())
									commitFound = true;
							}
							commitNotFound = !commitFound;
						} else {
