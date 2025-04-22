public interface RepositoryResolver<C> {
	public static final RepositoryResolver<?> NONE = new RepositoryResolver<Object>() {
		public Repository open(Object req
				throws RepositoryNotFoundException {
			throw new RepositoryNotFoundException(name);
		}
	};

