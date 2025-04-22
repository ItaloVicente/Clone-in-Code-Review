	private static <K, V> void prune(Map<K, Reference<V>> map) {
		for (final Iterator<Map.Entry<K, Reference<V>>> i = map.entrySet()
				.iterator(); i.hasNext();) {
			final Map.Entry<K, Reference<V>> e = i.next();
			if (e.getValue().get() == null) {
				i.remove();
			}
		}
	}

