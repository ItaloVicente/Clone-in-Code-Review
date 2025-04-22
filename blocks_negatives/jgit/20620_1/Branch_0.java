	private void addRefs(final Map<String, Ref> allRefs, final String prefix,
			final boolean add) {
		if (all || add) {
			for (final Ref ref : RefComparator.sort(allRefs.values())) {
				final String name = ref.getName();
				if (name.startsWith(prefix))
					addRef(name.substring(name.indexOf('/', 5) + 1), ref);
			}
