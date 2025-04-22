						@SuppressWarnings({ "unchecked", "cast" })
						TreePath<E>[] oldTreePathSelection = (TreePath<E>[]) oldSelection
								.toArray(new TreePath[oldSelection.size()]);

						setSelection(new TreeSelection(oldTreePathSelection,
								getComparer()), false);
