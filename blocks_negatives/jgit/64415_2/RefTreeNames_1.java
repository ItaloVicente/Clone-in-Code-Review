	/**
	 * Snapshot all references from a RefTreeDatabase and its bootstrap.
	 * <p>
	 * There may be name conflicts with multiple {@link Ref} objects containing
	 * the same name in the returned collection.
	 *
	 * @param refdb
	 *            database instance.
	 * @return all known references.
	 * @throws IOException
	 *             references cannot be enumerated.
	 */
	public static Collection<Ref> allRefs(RefDatabase refdb)
			throws IOException {
		Collection<Ref> refs = refdb.getRefs(ALL).values();
		if (!(refdb instanceof RefTreeDatabase)) {
			return refs;
		}

		RefDatabase bootstrap = ((RefTreeDatabase) refdb).getBootstrap();
		Collection<Ref> br = bootstrap.getRefs(ALL).values();
		List<Ref> all = new ArrayList<>(refs.size() + br.size());
		all.addAll(refs);
		all.addAll(br);
		return all;
	}

