				.setSafeForMacOS(safeForMacOS)
				.setSkipList(skipList());
		}

		private ObjectIdSet skipList() {
			if (fsckSkipList != null && !fsckSkipList.isEmpty()) {
				return new LazyObjectIdSetFile(new File(fsckSkipList));
			}
			return null;
