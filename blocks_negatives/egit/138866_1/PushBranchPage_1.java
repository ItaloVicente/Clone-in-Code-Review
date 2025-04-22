
	/**
	 * {@code FutureRefs} are loaded asynchronously from the upstream
	 * repository.
	 */
	private static class FutureRefs extends AsynchronousListOperation<Ref> {

		private final String localBranchName;

		public FutureRefs(Repository repository, String uriText,
				String localBranchName) {
			super(repository, uriText);
			this.localBranchName = localBranchName;
		}

		@Override
		protected Collection<Ref> convert(Collection<Ref> refs) {
			List<Ref> filtered = new ArrayList<>();
			String localFullName = localBranchName != null
					? Constants.R_HEADS + localBranchName : null;
			boolean localBranchFound = false;
			for (Ref ref : refs) {
				String name = ref.getName();
				if (name.startsWith(Constants.R_HEADS)) {
					filtered.add(ref);
					if (localFullName != null
							&& localFullName.equalsIgnoreCase(name)) {
						localBranchFound = true;
					}
				}
			}
			Collections.sort(filtered, CommonUtils.REF_ASCENDING_COMPARATOR);
			if (localFullName != null && !localBranchFound) {
				List<Ref> newRefs = new ArrayList<>(filtered.size() + 1);
				newRefs.add(new ObjectIdRef.Unpeeled(Storage.NEW, localFullName,
						ObjectId.zeroId()));
				newRefs.addAll(filtered);
				filtered = newRefs;
			}
			return filtered;
		}
	}

