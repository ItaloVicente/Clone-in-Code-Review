		ObjectId result = null;
		try {
			result = repo.resolve((startPoint == null) ? Constants.HEAD
					: startPoint);
		} catch (AmbiguousObjectException e) {
			throw e;
		}
