
	/**
	 * Sets the moveable attribute.
	 *
	 * @param moveable the moveable attribute
	 * @return this
	 *
	 * @see TableColumn#setMoveable(boolean)
	 *
	 * @since 3.19
	 */
	public TableColumnFactory moveable(boolean moveable) {
		addProperty(c -> c.setMoveable(moveable));
		return this;
	}

	/**
	 * Sets the resizable attribute.
	 *
	 * @param resizable the resize attribute
	 * @return this
	 *
	 * @see TableColumn#setResizable(boolean)
	 *
	 * @since 3.19
	 */
	public TableColumnFactory resizable(boolean resizable) {
		addProperty(c -> c.setResizable(resizable));
		return this;
	}
