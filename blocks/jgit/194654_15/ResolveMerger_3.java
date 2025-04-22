			workTreeUpdater.writeWorkTreeChanges(true);
			if (getUnmergedPaths().isEmpty() && !failed()) {
				WorkTreeUpdater.Result result = workTreeUpdater.writeIndexChanges();
				resultTree = result.treeId;
				modifiedFiles = result.modifiedFiles;
				for (String f : result.failedToDelete) {
					failingPaths.put(f
				}
				return result.failedToDelete.isEmpty();
			}
			resultTree = null;
