	public List<Ref> getRefsByPrefixWithSkips(Set<String> exclude
			throws IOException {
		List<Ref> all = new ArrayList<>();
		lock.lock();
		Reftable table = reader();
		for (String prefixToExclude : exclude) {
			try (RefCursor cursor = table.seekRefsWithPrefix(prefixToExclude)){
				if(cursor.next()) {
					exclude.remove(prefixToExclude);
				}
			}
		}
		Queue<String> sortedValidPrefixes = exclude.stream().sorted().collect(Collectors.toCollection(
				ArrayDeque::new));
		String currentPrefixToExclude = sortedValidPrefixes.isEmpty() ? null : sortedValidPrefixes.poll();
		try {
			try(RefCursor rc = RefDatabase.ALL.equals(include) ? table.allRefs()
					: table.seekRefsWithPrefix(include)) {
				while (rc.next()) {
					Ref ref = table.resolve(rc.getRef());
					if (ref == null || ref.getObjectId() == null) {
						continue;
					}
					if (currentPrefixToExclude != null && ref.getName().startsWith(currentPrefixToExclude)) {
						rc.seekPastPrefix(currentPrefixToExclude);
						currentPrefixToExclude =
								sortedValidPrefixes.isEmpty() ? null : sortedValidPrefixes.poll();
					} else {
						all.add(ref);
					}
				}
			}
		} finally {
			lock.unlock();
		}

		return Collections.unmodifiableList(all);
	}

