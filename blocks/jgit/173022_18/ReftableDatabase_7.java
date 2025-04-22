	public List<Ref> getRefsByPrefixWithSkips(String include
		if (excludes.isEmpty()) {
			return getRefsByPrefix(include);
		}
		List<Ref> results = new ArrayList<>();
		lock.lock();
		try {
			Reftable table = reader();
			Iterator<String> excludeIterator =
					excludes.stream().sorted().collect(Collectors.toList()).iterator();
			String currentExclusion = excludeIterator.hasNext() ? excludeIterator.next() : null;
			try (RefCursor rc = RefDatabase.ALL.equals(include) ? table.allRefs() : table.seekRefsWithPrefix(include)) {
				while (rc.next()) {
					Ref ref = table.resolve(rc.getRef());
					if (ref == null || ref.getObjectId() == null) {
						continue;
					}
					while (excludeIterator.hasNext() && !ref.getName().startsWith(currentExclusion)
							&& ref.getName().compareTo(currentExclusion) > 0) {
						currentExclusion = excludeIterator.next();
					}

					if (currentExclusion != null && ref.getName().startsWith(currentExclusion)) {
						rc.seekPastPrefix(currentExclusion);
						continue;
					}
					results.add(ref);
				}
			}
		} finally {
			lock.unlock();
		}

		return Collections.unmodifiableList(results);
	}

