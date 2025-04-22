
		@Override
		public Repository getRepository() {
			return repositoryRef.get();
		}

		@Override
		public ObjectId getObjectId() {
			if (fullId != null) {
				return fullId;
			}
			AbbreviatedObjectId shortId = line.getCommit();
			if (shortId.isComplete()) {
				fullId = shortId.toObjectId();
				return fullId;
			}
			Repository repo = getRepository();
			if (repo == null) {
				return null;
			}
			try {
				ObjectId id = repo.resolve(shortId.name());
				if (id != null) {
					commit = repo.parseCommit(id);
					fullId = id;
					return fullId;
				}
			} catch (IllegalArgumentException | IOException e) {
			}
			return null;
		}

		@Override
		public RevCommit getRevCommit() {
			if (commit != null) {
				return commit;
			}
			ObjectId id = getObjectId();
			if (commit != null) {
				return commit;
			}
			if (id == null) {
				return null;
			}
			Repository repo = getRepository();
			if (repo == null) {
				return null;
			}
			try {
				commit = repo.parseCommit(id);
				return commit;
			} catch (IOException e) {
				return null;
			}
		}
