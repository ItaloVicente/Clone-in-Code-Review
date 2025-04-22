	public List<Ref> getRefsByPrefix(String include
			throws IOException {
		List<Ref> all = new ArrayList<>();
		lock.lock();
		Reftable table = reader();
		exclude = getWithoutNonExistingPrefixes(exclude
		List<String> sortedValidPrefixesToExcludeWithoutOverlaps = getWithoutOverlaps(exclude);
		String currentPrefixToExclude =
				sortedValidPrefixesToExcludeWithoutOverlaps.isEmpty() ? null : sortedValidPrefixesToExcludeWithoutOverlaps.remove(0);
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
								sortedValidPrefixesToExcludeWithoutOverlaps.isEmpty() ? null : sortedValidPrefixesToExcludeWithoutOverlaps.remove(0);
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

	private List<String> getWithoutOverlaps(Set<String> exclude) {
		List<String> sortedValidPrefixesToExclude =
				exclude.stream().sorted().collect(Collectors.toList());
		List<String> sortedValidPrefixesToExcludeWithoutOverlaps = new ArrayList<>();
		String previousValidPrefix = null;
		for (String prefixToExclude : sortedValidPrefixesToExclude) {
			if (previousValidPrefix == null || !prefixToExclude.startsWith(previousValidPrefix)) {
				previousValidPrefix = prefixToExclude;
				sortedValidPrefixesToExcludeWithoutOverlaps.add(prefixToExclude);
			}
		}
		return sortedValidPrefixesToExcludeWithoutOverlaps;
	}

	private Set<String> getWithoutNonExistingPrefixes(Set<String> exclude
			throws IOException {
		Set<String> result = new HashSet<>();
		for (String prefixToExclude : exclude) {
			try (RefCursor cursor = table.seekRefsWithPrefix(prefixToExclude)){
				if(cursor.next()) {
					result.add(prefixToExclude);
				}
			}
		}
		return result;
	}

