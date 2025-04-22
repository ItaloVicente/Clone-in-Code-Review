		private String encoding;

		private CheckoutMetadata metadata;

		void fill(Repository repository, TreeWalk walk, String gitPath) {
			if (!filled) {
				filled = true;
				encoding = CompareCoreUtils.getResourceEncoding(repository,
						gitPath);
				try {
					metadata = new CheckoutMetadata(
							walk.getEolStreamType(
									TreeWalk.OperationType.CHECKOUT_OP),
							walk.getFilterCommand(
									Constants.ATTR_FILTER_TYPE_SMUDGE));
				} catch (IOException e) {
					throw new UncheckedIOException(e);
