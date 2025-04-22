			public void handleReplace(int index, E oldElement,
					E newElement) {
				List<E> first = result.value.subList(0, index);
				List<E> middle = Collections.singletonList(newElement);
				List<E> last = result.value.subList(index + 1, result.value.size());
				result.value = ConcatList.cat(first, middle, last);
