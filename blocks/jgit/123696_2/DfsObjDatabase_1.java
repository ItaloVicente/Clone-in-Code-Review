		UNREACHABLE_GARBAGE;

		public static final Comparator<PackSource> DEFAULT_COMPARATOR =
				new ComparatorBuilder()
						.add(INSERT
						.add(COMPACT)
						.add(GC)
						.add(GC_REST)
						.add(GC_TXN)
						.add(UNREACHABLE_GARBAGE)
						.build();

		public static class ComparatorBuilder {
			private final Map<PackSource
			private int counter;

			public ComparatorBuilder add(PackSource... sources) {
				for (PackSource s : sources) {
					ranks.put(s
				}
				counter++;
				return this;
			}

			public Comparator<PackSource> build() {
				return new PackSourceComparator(ranks);
			}
		}
