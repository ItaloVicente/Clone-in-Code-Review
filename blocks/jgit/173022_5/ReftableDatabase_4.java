	public List<Ref> getRefsExcludingPrefixes(Set<String> prefixes) throws IOException {
		List<Ref> all = new ArrayList<>();
		for (String prefix : prefixes) {
			if(getRefsByPrefix(prefix).isEmpty()){
				prefixes.remove(prefix);
			}
		}
		Queue<String> sortedValidPrefixes = prefixes.stream().sorted().collect(Collectors.toCollection(
				ArrayDeque::new));
		String currentPrefixToExclude = sortedValidPrefixes.isEmpty() ? null : sortedValidPrefixes.poll();
		lock.lock();
		try {
			Reftable table = reader();
			try(RefCursor rc = table.allRefs()) {
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

