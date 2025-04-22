	/**
	 * Dispose of all of the <code>Color</code>s in this iterator.
	 *
	 * @param iterator over <code>Collection</code> of <code>Color</code>
	 */
	private void disposeColors(Iterator<Color> iterator) {
		while (iterator.hasNext()) {
			Object next = iterator.next();
			((Color) next).dispose();
		}
	}

