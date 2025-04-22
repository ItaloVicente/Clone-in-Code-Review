
	private static class StreamTypeProviderImpl implements StreamTypeProvider {
		private final WorkingTreeOptions options;

		private final OperationType op;

		private final AttributesProvider attributesProvider;

		StreamTypeProviderImpl(Repository repo
				AttributesProvider attributesProvider) {
			this.options = repo.getConfig().get(WorkingTreeOptions.KEY);
			this.op = op;
			this.attributesProvider = attributesProvider;
		}

		@Override
		public StreamType getStreamType() {
			return StreamTypeUtil.detectStreamType(op
					attributesProvider.getAttributes());
		}
	}
