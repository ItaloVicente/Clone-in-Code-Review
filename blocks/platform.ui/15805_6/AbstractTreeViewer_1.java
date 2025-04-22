	private E castObjectToElement(Object object){
		@SuppressWarnings("unchecked")
		E element = (E) object;
		return element;
	}

	private TreePath<E> castObjectToTreePath(Object object){
		@SuppressWarnings("unchecked")
		TreePath<E> treePath = (TreePath<E>) object;
		return treePath;
	}
