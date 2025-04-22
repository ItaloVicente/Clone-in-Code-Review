public class ReflogViewContentProvider implements ITreeContentProvider {

	public static class ReflogInput {

		private final Repository repository;

		private final String ref;

		public ReflogInput(Repository repository, String ref) {
			Assert.isNotNull(repository, "Repository cannot be null"); //$NON-NLS-1$
			Assert.isNotNull(ref, "Ref cannot be null"); //$NON-NLS-1$
			this.repository = repository;
			this.ref = ref;
		}

		public Repository getRepository() {
			return repository;
		}

		public String getRef() {
			return ref;
		}
	}
