
	private boolean isMasterBranchAvailable() {
		try {
			return repository.getRepository().getRef(R_HEADS + master) != null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
