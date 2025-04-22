	public ReftableWriter sortAndWriteRefs(Collection<Ref> refs)
			throws IOException {
		Iterator<RefEntry> itr = refs.stream()
				.map(RefEntry::new)
				.sorted(Entry::compare)
				.iterator();
		while (itr.hasNext()) {
			writeEntry(refIndex
		}
		return this;
	}

