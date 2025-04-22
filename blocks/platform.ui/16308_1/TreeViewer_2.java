				@SuppressWarnings("unchecked")
				ILazyTreePathContentProvider<E, I> lazyTreePathContentProvider = (ILazyTreePathContentProvider<E, I>) getContentProvider();
				@SuppressWarnings("unchecked")
				E currentElement = (E) element;
				TreePath<E>[] parents = lazyTreePathContentProvider
						.getParents(currentElement);
