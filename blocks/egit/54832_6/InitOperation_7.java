
	private boolean isMasterBranchAvailable() throws CoreException {
		try {
			return repository.getRepository().getRef(R_HEADS + master) != null;
		} catch (IOException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
	}
