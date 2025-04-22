		@SuppressWarnings("unchecked")
		void dedupe(BinaryOperator<T> mergeFunction) {
			if (size == 0) {
				return;
			}
			int lastElement = 0;
			for (int i = 1; i < size; i++) {
				if (RefComparator.INSTANCE.compare(list[lastElement]
						list[i]) == 0) {
					list[lastElement] = mergeFunction
							.apply((T) list[lastElement]
				} else {
					list[lastElement + 1] = list[i];
					lastElement++;
				}
			}
			size = lastElement + 1;
			Arrays.fill(list
		}

