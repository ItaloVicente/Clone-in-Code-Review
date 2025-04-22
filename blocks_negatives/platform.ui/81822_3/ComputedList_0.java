			final List<E> oldList = new ArrayList<E>(cachedList);
			fireListChange(new ListDiff<E>() {
				List<ListDiffEntry<E>> differences;

				@Override
				public ListDiffEntry<E>[] getDifferences() {
					if (differences == null)
						return Diffs.computeListDiff(oldList, getList())
								.getDifferences();
					return differences.toArray(new ListDiffEntry[differences
							.size()]);
				}
			});
