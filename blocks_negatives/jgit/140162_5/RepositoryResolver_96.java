	RepositoryResolver<?> NONE = new RepositoryResolver<Object>() {
		@Override
		public Repository open(Object req, String name)
				throws RepositoryNotFoundException {
			throw new RepositoryNotFoundException(name);
		}
	};
