		try {
			return getRepository() != null
					&& getRepository().getRef(Constants.HEAD).getObjectId() != null;
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
