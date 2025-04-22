				try (RevWalk rw = new RevWalk(repo)) {
					ObjectId stashId = repo.resolve(stash);
					RevCommit commit = rw.parseCommit(stashId);
					updateStashRef(commit
							commit.getShortMessage());
				}
