				rw.markStart(commit);
				rw.setTreeFilter(AndTreeFilter.create(
						PathFilter.create(gitPath), TreeFilter.ANY_DIFF));
				RevCommit latestFileCommit = rw.next();
				if (latestFileCommit == null)
					latestFileCommit = commit;
