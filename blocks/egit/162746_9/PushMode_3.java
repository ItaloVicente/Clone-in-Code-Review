	GERRIT {
		@Override
		public Wizard getWizard(@NonNull Repository repository,
				RevCommit commit)
				throws IOException {
			Ref ref = repository.exactRef(Constants.HEAD);
			if (ref != null) {
				return new PushToGerritWizard(repository);
			}
			return null;
		}
	};

	public abstract Wizard getWizard(@NonNull Repository repository,
			RevCommit commit) throws IOException;
