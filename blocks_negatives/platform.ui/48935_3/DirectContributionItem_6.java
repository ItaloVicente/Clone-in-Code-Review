	/**
	 * Return a context for this part.
	 *
	 * @param part
	 *            the part to start searching from
	 * @return the closest context, or global context if none in the hierarchy
	 */
	protected IEclipseContext getContext(MUIElement part) {
		if (part instanceof MContext) {
			return ((MContext) part).getContext();
		}
		return getContextForParent(part);
