	public List<Ref> getRefsByPrefixWithSkips(String include
		List<Ref> all = new ArrayList<>();
		lock.lock();
		try {
			Reftable table = reader();
			Iterator<String> sortedPrefixesToExclude = exclude.stream().sorted().collect(Collectors.toList())
					.iterator();
			String currentPrefixToExclude = sortedPrefixesToExclude.hasNext() ? sortedPrefixesToExclude.next() : null;
			try (RefCursor rc = RefDatabase.ALL.equals(include) ? table.allRefs() : table.seekRefsWithPrefix(include)) {
				while (rc.next()) {
					Ref ref = table.resolve(rc.getRef());
					if (ref == null || ref.getObjectId() == null) {
						continue;
					}
					while (sortedPrefixesToExclude.hasNext() && !ref.getName().startsWith(currentPrefixToExclude)
							&& ref.getName().compareTo(currentPrefixToExclude) > 0) {
						currentPrefixToExclude = sortedPrefixesToExclude.next();
					}

					if (currentPrefixToExclude != null && ref.getName().startsWith(currentPrefixToExclude)) {
						rc.seekPastPrefix(currentPrefixToExclude);
						continue;
					}
					all.add(ref);
				}
			}
		} finally {
			lock.unlock();
		}

		return Collections.unmodifiableList(all);
	}

