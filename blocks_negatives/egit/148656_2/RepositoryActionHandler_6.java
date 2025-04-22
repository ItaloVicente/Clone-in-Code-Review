		try {
			return repository != null ? repository.resolve(Constants.HEAD) != null
					: false;
		} catch (Exception e) {
		}

		return false;
