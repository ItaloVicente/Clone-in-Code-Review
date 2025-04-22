			if (refName != null) {
				newCommit = walk.parseCommit(repository.resolve(refName))
						.asCommit(walk);
			}
			if (commitId != null) {
				newCommit = walk.parseCommit(commitId).asCommit(walk);
			}
