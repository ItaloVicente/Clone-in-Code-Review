	private RepositoryMappingChangeListener mappingChangeListener = new RepositoryMappingChangeListener() {

		@Override
		public void repositoryChanged(RepositoryMapping which) {
			fireLabelEvent();
		}

	};

