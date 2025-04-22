
	protected boolean containsHead() {
		try {
			return getRepository().resolve(Constants.HEAD) != null;
		} catch (Exception e) {
		}

		return false;
	}
