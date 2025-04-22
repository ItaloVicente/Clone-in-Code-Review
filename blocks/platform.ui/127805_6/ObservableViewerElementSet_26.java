		Set<E> removals = new ViewerElementSet<E>(comparer);
		@SuppressWarnings("unchecked")
		E[] toRetain = (E[]) c.toArray();
		outer: for (Iterator<?> iterator = wrappedSet.iterator(); iterator.hasNext();) {
			@SuppressWarnings("unchecked")
			E element = (E) iterator.next();
