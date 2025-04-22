			if (!filepaths.isEmpty()) {
				resetIndexForPaths(commit);
				setCallable(false);
				return repo.getRef(Constants.HEAD);
			}

