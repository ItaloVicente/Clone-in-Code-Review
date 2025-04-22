		event.diff.accept(new ListDiffVisitor<E>() {
			@Override
			public void handleAdd(int index, E element) {
				cachedSizes.compute(index, (i, size) -> size == null ? 1 : size + 1);
			}

			@Override
			public void handleRemove(int index, E element) {
				cachedSizes.compute(index, (i, size) -> size - 1);
			}
		});

