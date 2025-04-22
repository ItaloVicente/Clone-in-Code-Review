	public class WritableList<E> extends ObservableList<E> {
	
		public WritableList() {
			this(Realm.getDefault());
		}
	
		public WritableList(Realm realm) {
			this(realm, new ArrayList<E>(), null);
		}
	
		public WritableList(List<E> toWrap, Object elementType) {
			this(Realm.getDefault(), toWrap, elementType);
		}
	
		public WritableList(Collection<E> collection, Object elementType) {
			this(Realm.getDefault(), new ArrayList<E>(collection), elementType);
		}
	
		public WritableList(Realm realm, List<E> toWrap, Object elementType) {
			super(realm, toWrap, elementType);
		}
	
		public WritableList(Realm realm, Collection<E> collection,
				Object elementType) {
			super(realm, new ArrayList<E>(collection), elementType);
		}
	
		@Override
		public E set(int index, E element) {
			checkRealm();
			E oldElement = wrappedList.set(index, element);
			fireListChange(Diffs.createListDiff(
					Diffs.createListDiffEntry(index, false, oldElement),
					Diffs.createListDiffEntry(index, true, element)));
			return oldElement;
		}
	
		@Override
		public E move(int oldIndex, int newIndex) {
			checkRealm();
			int size = wrappedList.size();
			if (oldIndex < 0 || oldIndex >= size)
				throw new IndexOutOfBoundsException(
						"oldIndex: " + oldIndex + ", size:" + size); //$NON-NLS-1$ //$NON-NLS-2$
			if (newIndex < 0 || newIndex >= size)
				throw new IndexOutOfBoundsException(
						"newIndex: " + newIndex + ", size:" + size); //$NON-NLS-1$ //$NON-NLS-2$
			if (oldIndex == newIndex)
				return wrappedList.get(oldIndex);
			E element = wrappedList.remove(oldIndex);
			wrappedList.add(newIndex, element);
			fireListChange(Diffs.createListDiff(
					Diffs.createListDiffEntry(oldIndex, false, element),
					Diffs.createListDiffEntry(newIndex, true, element)));
			return element;
		}
	
		@Override
		public E remove(int index) {
			checkRealm();
			E oldElement = wrappedList.remove(index);
			fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(index,
					false, oldElement)));
			return oldElement;
		}
	
		@Override
		public boolean add(E element) {
			checkRealm();
			boolean added = wrappedList.add(element);
			if (added) {
				fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(
						wrappedList.size() - 1, true, element)));
			}
			return added;
		}
	
		@Override
		public void add(int index, E element) {
			checkRealm();
			wrappedList.add(index, element);
			fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(index,
					true, element)));
		}
	
		@Override
		public boolean addAll(Collection<? extends E> c) {
			checkRealm();
			List<ListDiffEntry<E>> entries = new ArrayList<ListDiffEntry<E>>(
					c.size());
			int addIndex = wrappedList.size();
			for (Iterator<? extends E> it = c.iterator(); it.hasNext();) {
				E element = it.next();
				entries.add(Diffs.createListDiffEntry(addIndex++, true, element));
			}
			boolean added = wrappedList.addAll(c);
			fireListChange(Diffs.createListDiff(entries));
			return added;
		}
	
		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			checkRealm();
			List<ListDiffEntry<E>> entries = new ArrayList<ListDiffEntry<E>>(
					c.size());
			int addIndex = index;
			for (Iterator<? extends E> it = c.iterator(); it.hasNext();) {
				E element = it.next();
				entries.add(Diffs.createListDiffEntry(addIndex++, true, element));
			}
			boolean added = wrappedList.addAll(index, c);
			fireListChange(Diffs.createListDiff(entries));
			return added;
		}
	
		@Override
		public boolean remove(Object o) {
			checkRealm();
			int index = wrappedList.indexOf(o);
			if (index == -1) {
				return false;
			}
	
			E typedO = wrappedList.get(index);
	
			wrappedList.remove(index);
			fireListChange(Diffs.createListDiff(Diffs.createListDiffEntry(index,
					false, typedO)));
			return true;
		}
	
		@Override
		public boolean removeAll(Collection<?> c) {
			checkRealm();
			List<ListDiffEntry<E>> entries = new ArrayList<ListDiffEntry<E>>();
			for (Iterator<?> it = c.iterator(); it.hasNext();) {
				Object element = it.next();
				int removeIndex = wrappedList.indexOf(element);
				if (removeIndex != -1) {
					E removedElement = wrappedList.get(removeIndex);
					wrappedList.remove(removeIndex);
					entries.add(Diffs.createListDiffEntry(removeIndex, false,
							removedElement));
				}
			}
			if (entries.size() > 0)
				fireListChange(Diffs.createListDiff(entries));
			return entries.size() > 0;
		}
	
		@Override
		public boolean retainAll(Collection<?> c) {
			checkRealm();
			List<ListDiffEntry<E>> entries = new ArrayList<ListDiffEntry<E>>();
			int removeIndex = 0;
			for (Iterator<E> it = wrappedList.iterator(); it.hasNext();) {
				E element = it.next();
				if (!c.contains(element)) {
					entries.add(Diffs.createListDiffEntry(removeIndex, false,
							element));
					it.remove();
				} else {
					removeIndex++;
				}
			}
			if (entries.size() > 0)
				fireListChange(Diffs.createListDiff(entries));
			return entries.size() > 0;
		}
	
		@Override
		public void clear() {
			checkRealm();
			List<ListDiffEntry<E>> entries = new ArrayList<ListDiffEntry<E>>(
					wrappedList.size());
			for (ListIterator<E> it = wrappedList.listIterator(wrappedList.size()); it
					.hasPrevious();) {
				int elementIndex = it.previousIndex();
				E element = it.previous();
				entries.add(Diffs.createListDiffEntry(elementIndex, false, element));
			}
			wrappedList.clear();
			fireListChange(Diffs.createListDiff(entries));
		}
	
		public static <T> WritableList<T> withElementType(Object elementType) {
			return new WritableList<T>(Realm.getDefault(), new ArrayList<T>(), elementType);
		}
	}

