			} else {
				RevCommit commit;
				if (name != null && !createBranch) {
					ObjectId branch = repo.resolve(name);
					commit = revWalk.parseCommit(branch);
				} else {
					commit = revWalk.parseCommit(getStartPointObjectId());
				}
