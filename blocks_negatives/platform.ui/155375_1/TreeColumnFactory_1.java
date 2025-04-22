
	/**
	 * Sets the moveable attribute.
	 *
	 * @param moveable the moveable attribute
	 * @return this
	 *
	 * @see TreeColumn#setMoveable(boolean)
	 *
	 * @since 3.19
	 */
	public TreeColumnFactory moveable(boolean moveable) {
		addProperty(c -> c.setMoveable(moveable));
		return this;
	}

	/**
	 * Sets the resizable attribute.
	 *
	 * @param resizable the resize attribute
	 * @return this
	 *
	 * @see TreeColumn#setResizable(boolean)
	 *
	 * @since 3.19
	 */
	public TreeColumnFactory resizable(boolean resizable) {
		addProperty(c -> c.setResizable(resizable));
		return this;
	}
