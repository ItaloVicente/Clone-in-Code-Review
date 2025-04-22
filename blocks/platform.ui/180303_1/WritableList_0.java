	@Override
	public java.util.Iterator<E> iterator() {

		getterCalled();
		final Iterator<E> wrappedIterator = wrappedList.iterator();
		return new Iterator<E>() {

			private E current;

			@Override
			public void remove() {

				int index = wrappedList.indexOf(current);
				wrappedIterator.remove();
				if (index == -1) {
					return;
				}
				fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(index, false, current)));
			}

			@Override
			public boolean hasNext() {

				return wrappedIterator.hasNext();
			}

			@Override
			public E next() {

				return current = wrappedIterator.next();
			}
		};
	}

	@Override
	public ListIterator<E> listIterator(int index) {

		getterCalled();
		final ListIterator<E> wrappedIterator = wrappedList.listIterator(index);
		return new ListIterator<E>() {

			private E current;

			@Override
			public int nextIndex() {

				return wrappedIterator.nextIndex();
			}

			@Override
			public int previousIndex() {

				return wrappedIterator.previousIndex();
			}

			@Override
			public void remove() {

				int index = wrappedList.indexOf(current);
				wrappedIterator.remove();
				if (index == -1) {
					return;
				}
				fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(index, false, current)));
			}

			@Override
			public boolean hasNext() {

				return wrappedIterator.hasNext();
			}

			@Override
			public boolean hasPrevious() {

				return wrappedIterator.hasPrevious();
			}

			@Override
			public E next() {

				return current = wrappedIterator.next();
			}

			@Override
			public E previous() {

				return current = wrappedIterator.previous();
			}

			@Override
			public void add(E o) {

				int indexOf = wrappedList.indexOf(current);
				wrappedIterator.add(o);
				fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(indexOf + 1, true, o)));
				current = o;
			}

			@Override
			public void set(E o) {

				wrappedIterator.set(o);
				fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(index, false, current),
						Diffs.createListDiffEntry(index, true, o)));
			}
		};
	}

