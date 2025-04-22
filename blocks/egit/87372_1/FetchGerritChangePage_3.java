	private Ref getLocalRef(String ref) {
		Change change = Change.fromRef(ref);
		if (change != null) {
			try {
				return repository.findRef(change.computeFullRefName());
			} catch (IOException e) {
			}
		}
		return null;
	}

