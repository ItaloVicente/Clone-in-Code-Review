			ObjectId objectId = repository.resolve(Constants.HEAD);
			if (objectId == null) {
				Activator.handleError(
						UIText.CompareUtils_errorGettingHeadCommit, null, true);
				return null;
			}
			headCommit = new RevWalk(repository).parseCommit(objectId);
