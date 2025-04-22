	 */
	public <T> List<T> findElements(MUIElement searchRoot, String id, Class<T> clazz,
			List<String> tagsToMatch, int searchFlags);

	/**
	 * Return a list of any elements that match the given search criteria. The search is recursive
	 * and includes the specified search root. Any of the search parameters may be specified as
	 * <code>null</code> in which case that field will always 'match'.
	 * <p>
	 * NOTE: This is a generically typed method with the List's generic type expected to be the
	 * value of the 'clazz' parameter. If the 'clazz' parameter is null then the returned list is
	 * untyped but may safely be assigned to List&lt;MUIElement&gt;.
	 * </p>
