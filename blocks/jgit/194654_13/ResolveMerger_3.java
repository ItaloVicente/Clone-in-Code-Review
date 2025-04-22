			ioHandler.finishBuilding(true);
			if (getUnmergedPaths().isEmpty() && !failed()) {
				RepoIOHandler.Result result = ioHandler.writeChanges();
				resultTree = result.treeId;
				modifiedFiles = result.modifiedFiles;
				for (String f : result.failedToDelete) {
					failingPaths.put(f
				}
				return result.failedToDelete.isEmpty();
			}
			resultTree = null;
