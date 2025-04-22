
	private static class FutureRefs extends AsynchronousListOperation<Ref> {

		public FutureRefs(Repository repository, String uriText) {
			super(repository, uriText,
					UIText.FetchGerritChangePage_FetchingRemoteRefsMessage);
		}

		@Override
		protected Collection<Ref> convert(Collection<Ref> refs) {
			return refs;
		}
	}

