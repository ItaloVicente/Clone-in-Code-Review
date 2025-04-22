			resultTree = ioHandler.writeChanges();
			List<String> failedToDelete = ioHandler.getFailedToDelete();
			for (String f : failedToDelete) {
				failingPaths.put(f
			}
			return failedToDelete.isEmpty();
