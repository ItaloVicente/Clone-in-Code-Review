	private String getCurrentBranchhName() {
		try {
			return repository.getRepository().getBranch();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

