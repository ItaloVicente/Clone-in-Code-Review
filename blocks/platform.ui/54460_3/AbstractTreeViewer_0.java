					if (element instanceof TreePath) {
						@SuppressWarnings("unchecked")
						TreePath<E> parentTreePath = (TreePath<E>) parentElementOrTreePath;
						E parentElement = parentTreePath.getLastSegment();
						updatePlus(item, parentElement);
					} else {
						@SuppressWarnings("unchecked")
						E parentElement = (E) parentElementOrTreePath;
						updatePlus(item, parentElement);
					}
