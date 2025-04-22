			isUnbornHead = false;
			if (repository.getRepositoryState() == RepositoryState.SAFE) {
				try {
					Ref head = repository.exactRef(Constants.HEAD);
					if (head != null && head.isSymbolic()
							&& head.getObjectId() == null) {
						isUnbornHead = true;
					}
					currentBranch = repository.getBranch();
				} catch (IOException e) {
					Activator.logError(e.getLocalizedMessage(), e);
				}
			}
