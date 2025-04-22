	public static <T extends Ref> Collector<T
			@Nullable BinaryOperator<T> mergeFunction) {
		return Collector.of(
				() -> new Builder<>()
				Builder<T>::add
					Builder<T> b = new Builder<>();
					b.addAll(b1);
					b.addAll(b2);
					return b;
				}
					if (mergeFunction != null) {
						b.sort();
						b.dedupe(mergeFunction);
					}
					return b.toRefList();
				});
	}

