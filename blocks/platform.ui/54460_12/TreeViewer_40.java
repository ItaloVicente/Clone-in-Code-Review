						@SuppressWarnings({ "unchecked" })
						TreePath<E>[] oldTreePathSelection = oldSelection
								.toArray(new TreePath[oldSelection.size()]);

						setSelection(new TreeSelection(oldTreePathSelection,
								getComparer()), false);
