		WorkingTreeIterator iterator = null;
		try {
			iterator = IteratorService.createInitialIterator(repository);
		} catch (IllegalStateException e) {
			return null;
		}
