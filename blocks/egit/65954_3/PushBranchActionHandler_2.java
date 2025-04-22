		try {
			Ref head = repository.exactRef(Constants.HEAD);
			if (head != null && head.getObjectId() != null) {
				return true;
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
