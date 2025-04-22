						List<ListDiffEntry<E>> entries = new ArrayList<>(2);
						entries.add(Diffs.createListDiffEntry(subListIndex,
								false, oldElement));
						entries.add(Diffs.createListDiffEntry(subListIndex,
								true, newElement));
						ListDiff<E> diff = Diffs.createListDiff(entries);
