			public void handleReplace(int index, Object oldElement,
					Object newElement) {
				List first = result[0].subList(0, index);
				List middle = Collections.singletonList(newElement);
				List last = result[0].subList(index + 1, result[0].size());
				result[0] = ConcatList.cat(first, middle, last);
