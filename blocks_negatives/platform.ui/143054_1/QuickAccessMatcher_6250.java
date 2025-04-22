	/**
	 * Returns the id for this element. The id has to be unique within the
	 * QuickAccessProvider that provided this element.
	 *
	 * @return the id
	 */
	public abstract String getId();

	/**
	 * Executes the associated action for this element.
	 */
	public abstract void execute();

	/**
	 * Return the label to be used for sorting elements.
	 *
	 * @return the sort label
	 */
	public String getSortLabel() {
		return getLabel();
	}
