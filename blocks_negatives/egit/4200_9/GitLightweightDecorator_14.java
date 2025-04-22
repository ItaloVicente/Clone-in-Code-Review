		postLabelEvent(null, true);
	}

	/**
	 * Post a label event to the LabelEventJob
	 *
	 * Posts a label event for specific elements. Does not invalidate other
	 * decorations. Same as <code>postLabelEvent(elements, false)</code>.
	 *
	 * @param elements
	 *            The elements to update
	 */
	private void postLabelEvent(final Object[] elements) {
		postLabelEvent(elements, false);
	}

	private void postLabelEvent(final Object[] elements,
			final boolean invalidateAllDecorations) {
