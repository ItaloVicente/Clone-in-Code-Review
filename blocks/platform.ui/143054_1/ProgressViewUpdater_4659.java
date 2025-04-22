		void remove(JobTreeElement removal) {
			deletions.add(removal);
		}

		void refresh(JobTreeElement refresh) {
			refreshes.add(refresh);
		}

		void reset() {
			additions.clear();
			deletions.clear();
			refreshes.clear();
			updateAll = false;
		}

		void processForUpdate() {
			HashSet<JobTreeElement> staleAdditions = new HashSet<>();

			Iterator<JobTreeElement> additionsIterator = additions.iterator();
			while (additionsIterator.hasNext()) {
