
	@SuppressWarnings({"nls"
	private void getRefsExcludingWithFilter(String prefix) throws Exception {
		long startTime = System.nanoTime();
		List<Ref> allRefs = new ArrayList<>();
		try (FileInputStream in = new FileInputStream(reftablePath);
				BlockSource src = BlockSource.from(in);
				ReftableReader reader = new ReftableReader(src)) {
			try (RefCursor rc = reader.allRefs()) {
				while (rc.next()) {
					allRefs.add(rc.getRef());
				}
			}
		}
		int total = allRefs.size();
		allRefs = allRefs.stream().filter(r -> r.getName().startsWith(prefix)).collect(Collectors.toList());
		int notStartWithPrefix = allRefs.size();
		int startWithPrefix = total - notStartWithPrefix;
		long totalTime = System.nanoTime() - startTime;
		printf("total time the action took using filter: %10d usec"
		printf("number of refs that start with prefix: %d"
		printf("number of refs that don't start with prefix: %d"
	}

	@SuppressWarnings({"nls"
	private void getRefsExcludingWithSeekPast(String prefix) throws Exception {
		long start = System.nanoTime();
		try (FileInputStream in = new FileInputStream(reftablePath);
				BlockSource src = BlockSource.from(in);
				ReftableReader reader = new ReftableReader(src)) {
			try (RefCursor rc = reader.allRefs()) {
				while (rc.next()) {
					if (rc.getRef().getName().startsWith(prefix)) {
						break;
					}
				}
				rc.seekPastPrefix(prefix);
				while (rc.next()) {
					rc.getRef();
				}
			}
		}
		long tot = System.nanoTime() - start;
		printf("total time the action took using seek: %10d usec"
	}
