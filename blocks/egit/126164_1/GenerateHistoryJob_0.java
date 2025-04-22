					if (commitToLoad != null) {
						loadedCommits.fillTo(commitToLoad, maxCommits);
						commitToShow = commitToLoad;
						commitToLoad = null;
						boolean commitFound = false;
						for (RevCommit commit : loadedCommits) {
							if (commit.getId().equals(commitToShow.getId())) {
								commitFound = true;
