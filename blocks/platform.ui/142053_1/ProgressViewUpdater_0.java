	static class UpdatesInfo {

		Collection<JobTreeElement> additions = new HashSet<>();

		Collection<JobTreeElement> deletions = new HashSet<>();

		Collection<JobTreeElement> refreshes = new HashSet<>();

		boolean updateAll = false;

		private UpdatesInfo() {
		}

		void add(JobTreeElement addition) {
			additions.add(addition);
		}

		void remove(JobTreeElement removal) {
			deletions.add(removal);
		}
