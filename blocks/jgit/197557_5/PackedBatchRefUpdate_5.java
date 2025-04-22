	private static class RefListUpdates {
		private final RefList<Ref> upserts;

		private final List<String> deletions;

		RefListUpdates(RefList<Ref> upserts
			this.upserts = upserts;
			this.deletions = deletions;
		}

		RefList<Ref> getUpserts() {
			return upserts;
		}

		List<String> getDeletions() {
			return deletions;
		}
	}

	private static RefListUpdates applyUpdates(RevWalk walk
