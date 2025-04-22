				ObjectId id = repository.resolve(refName);
				if (id == null) {
					return base;
				}
				try (RevWalk rw = new RevWalk(repository)) {
					RevCommit commit = rw.parseCommit(id);
					compareString = commit.getId().name();
