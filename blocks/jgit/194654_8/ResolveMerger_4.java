			ioHandler.finishBuilding(true);
			if (getUnmergedPaths().isEmpty() && !failed()) {
				resultTree = ioHandler.writeChangesAndCleanUp();
				List<String> failedToDelete = ioHandler.getFailedToDelete();
				for (String f : failedToDelete) {
					failingPaths.put(f
				}
				return failedToDelete.isEmpty();
			}
			resultTree = null;
			return false;
		} finally {
			ioHandler.cleanUpCache();
