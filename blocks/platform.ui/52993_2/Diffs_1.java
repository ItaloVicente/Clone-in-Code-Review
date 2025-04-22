	public static <K, V> MapDiff<K, V> createMapDiff(Set<? extends K> addedKeys, Set<? extends K> removedKeys,
			Set<? extends K> changedKeys, final Map<? extends K, ? extends V> oldValues,
			final Map<? extends K, ? extends V> newValues) {
		final Set<K> finalAddedKeys = Collections.unmodifiableSet(addedKeys);
		final Set<K> finalRemovedKeys = Collections.unmodifiableSet(removedKeys);
		final Set<K> finalChangedKeys = Collections.unmodifiableSet(changedKeys);
