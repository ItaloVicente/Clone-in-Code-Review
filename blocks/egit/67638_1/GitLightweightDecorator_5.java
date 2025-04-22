	private RepositoryChangeListener mappingChangeListener = new RepositoryChangeListener() {

		@Override
		public void repositoryChanged(RepositoryMapping which) {
			fireLabelEvent();
		}

	};

