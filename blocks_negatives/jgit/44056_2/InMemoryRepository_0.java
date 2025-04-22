		super(new DfsRepositoryBuilder<DfsRepositoryBuilder, InMemoryRepository>() {
			@Override
			public InMemoryRepository build() throws IOException {
				throw new UnsupportedOperationException();
			}
		}.setRepositoryDescription(repoDesc));
