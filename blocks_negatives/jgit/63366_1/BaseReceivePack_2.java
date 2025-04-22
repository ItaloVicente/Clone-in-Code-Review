
		ObjectChecker newObjectChecker() {
			if (!checkReceivedObjects)
				return null;
			return new ObjectChecker()
				.setAllowLeadingZeroFileMode(allowLeadingZeroFileMode)
				.setAllowInvalidPersonIdent(allowInvalidPersonIdent)
				.setSafeForWindows(safeForWindows)
				.setSafeForMacOS(safeForMacOS)
				.setSkipList(skipList());
		}

		private ObjectIdSet skipList() {
			if (fsckSkipList != null && !fsckSkipList.isEmpty()) {
				return new LazyObjectIdSetFile(new File(fsckSkipList));
			}
			return null;
		}
