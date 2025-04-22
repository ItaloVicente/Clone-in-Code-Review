		if (subRepo == null)
			return null;
		try {
			return subRepo.resolve(Constants.HEAD);
		} finally {
			subRepo.close();
		}
