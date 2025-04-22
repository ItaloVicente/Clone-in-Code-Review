	public void packRefs() throws IOException {
		Collection<Ref> refs = repo.getAllRefs().values();
		ArrayList<String> refsToBePacked = new ArrayList<String>(refs.size());
		int packRefsCnt = 0;
		pm.beginTask(JGitText.get().packRefs
		try {
			for (Ref ref : refs) {
				if (!ref.isSymbolic() && ref.getStorage().isLoose()) {
					refsToBePacked.add(ref.getName());
					packRefsCnt++;
				}
				pm.update(1);
			}
			((RefDirectory) repo.getRefDatabase()).pack(refsToBePacked
					.toArray(new String[packRefsCnt]));
		} finally {
			pm.endTask();
		}
	}

