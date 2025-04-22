		ObjectId headId;
		try {
			headId = subRepo.resolve(Constants.HEAD);
		} finally {
			subRepo.close();
		}
