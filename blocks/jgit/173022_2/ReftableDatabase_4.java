	public List<Ref> getRefsExcludingPrefixes(Set<String> prefixes) throws IOException {
		List<Ref> all = new ArrayList<>();
		lock.lock();
		try {
			Reftable table = reader();
			RefCursor rc = table.allRefs();
			while (rc.next()) {
				Ref ref = table.resolve(rc.getRef());
				if (ref == null || ref.getObjectId() == null) {
					continue;
				}
				String currentPrefixToExclude = null;
				for(String prefix : prefixes){
					if(ref.getName().startsWith(prefix)){
						currentPrefixToExclude = prefix;
						break;
					}
				}
				if (currentPrefixToExclude != null) {
					rc.close();
					rc = table.seekPastRef(currentPrefixToExclude);
				} else {
					all.add(ref);
				}
			}
			rc.close();
		} finally {
			lock.unlock();
		}

		return Collections.unmodifiableList(all);
	}

