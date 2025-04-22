		if (subRepo == null)
			return null;
		ObjectId head;
		try {
			head = subRepo.resolve(Constants.HEAD);
		} finally {
			subRepo.close();
		}
		return head;
