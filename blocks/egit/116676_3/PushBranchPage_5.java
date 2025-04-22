
	private static class FutureRefs extends AsynchronousListOperation<Ref> {

		public FutureRefs(Repository repository, String uriText) {
			super(repository, uriText,
					UIText.FetchGerritChangePage_FetchingRemoteRefsMessage);
		}

		@Override
		protected Collection<Ref> convert(Collection<Ref> refs) {
			List<Ref> filtered = new ArrayList<>();
			for (Ref ref : refs) {
				String name = ref.getName();
				if (name.startsWith(Constants.R_HEADS)) {
					filtered.add(ref);
				}
			}
			Collections.sort(filtered, CommonUtils.REF_ASCENDING_COMPARATOR);
			return filtered;
		}
	}

