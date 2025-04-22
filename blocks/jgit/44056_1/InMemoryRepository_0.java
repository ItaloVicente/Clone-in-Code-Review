	public static class Builder
			extends DfsRepositoryBuilder<Builder
		@Override
		public InMemoryRepository build() throws IOException {
			return new InMemoryRepository(this);
		}
	}

