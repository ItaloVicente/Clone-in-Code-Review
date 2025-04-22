	private static final class UnmodifiableListDiff<E> extends ListDiff<E> {
		private ListDiff<? extends E> toWrap;

		public UnmodifiableListDiff(ListDiff<? extends E> diff) {
			this.toWrap = diff;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ListDiffEntry<E>[] getDifferences() {
			ListDiffEntry<? extends E>[] original = toWrap.getDifferences();
			ListDiffEntry<?>[] result = new ListDiffEntry<?>[original.length];

			for (int idx = 0; idx < original.length; idx++) {
				result[idx] = original[idx];
			}
			return (ListDiffEntry<E>[]) result;
		}
	}

	private static final class UnmodifiableSetDiff<E> extends SetDiff<E> {
		private SetDiff<? extends E> toWrap;

		public UnmodifiableSetDiff(SetDiff<? extends E> diff) {
			toWrap = diff;
		}

		@Override
		public Set<E> getAdditions() {
			return Collections.unmodifiableSet(toWrap.getAdditions());
		}

		@Override
		public Set<E> getRemovals() {
			return Collections.unmodifiableSet(toWrap.getRemovals());
		}
	}

	private static final class UnmodifiableMapDiff<K, V> extends MapDiff<K, V> {
		private MapDiff<? extends K, ? extends V> toWrap;

		public UnmodifiableMapDiff(MapDiff<? extends K, ? extends V> diff) {
			toWrap = diff;
		}

		@Override
		public Set<K> getAddedKeys() {
			return Collections.unmodifiableSet(toWrap.getAddedKeys());
		}

		@Override
		public Set<K> getRemovedKeys() {
			return Collections.unmodifiableSet(toWrap.getRemovedKeys());
		}

		@Override
		public Set<K> getChangedKeys() {
			return Collections.unmodifiableSet(toWrap.getChangedKeys());
		}

		@Override
		public V getOldValue(Object key) {
			return toWrap.getOldValue(key);
		}

		@Override
		public V getNewValue(Object key) {
			return toWrap.getNewValue(key);
		}
	}

	private static final class UnmodifiableValueDiff<E> extends ValueDiff<E> {
		private ValueDiff<? extends E> toWrap;

		public UnmodifiableValueDiff(ValueDiff<? extends E> diff) {
			toWrap = diff;
		}

		@Override
		public E getOldValue() {
			return toWrap.getOldValue();
		}

		@Override
		public E getNewValue() {
			return toWrap.getNewValue();
		}
	}

	@SuppressWarnings("unchecked")
	public static <E> ListDiff<E> unmodifiableDiff(ListDiff<? extends E> diff) {
		if (diff instanceof UnmodifiableListDiff) {
			return (ListDiff<E>) diff;
		}

		return new UnmodifiableListDiff<E>(diff);
	}

	@SuppressWarnings("unchecked")
	public static <E> SetDiff<E> unmodifiableDiff(SetDiff<? extends E> diff) {
		if (diff instanceof UnmodifiableSetDiff) {
			return (SetDiff<E>) diff;
		}

		return new UnmodifiableSetDiff<E>(diff);
	}

	@SuppressWarnings("unchecked")
	public static <K, V> MapDiff<K, V> unmodifiableDiff(MapDiff<? extends K, ? extends V> diff) {
		if (diff instanceof UnmodifiableMapDiff) {
			return (MapDiff<K, V>) diff;
		}

		return new UnmodifiableMapDiff<K, V>(diff);
	}

	@SuppressWarnings("unchecked")
	public static <V> ValueDiff<V> unmodifiableDiff(ValueDiff<? extends V> diff) {
		if (diff instanceof UnmodifiableValueDiff) {
			return (ValueDiff<V>) diff;
		}

		return new UnmodifiableValueDiff<V>(diff);
	}
