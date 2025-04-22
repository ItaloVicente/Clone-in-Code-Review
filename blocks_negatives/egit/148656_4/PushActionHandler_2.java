		try {
			Repository repository = getRepository();
			if (repository == null) {
				return false;
			}
			Ref ref = repository.exactRef(Constants.HEAD);
			return ref != null && ref.getObjectId() != null;
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, false);
