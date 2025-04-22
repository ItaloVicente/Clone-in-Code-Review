	/**
	 * Creates a {@link String} representing the elements in <code>items</code> as a
	 * list. This method uses the {@link Object#toString()} method on the objects to
	 * create them as a String.
	 *
	 * @param items the List to make into a String
	 * @return a string which presents <code>items</code> in String form.
	 */
	public static String createList(List items) {
		String list = null;
		for (Iterator i = items.iterator(); i.hasNext();) {
			Object object = i.next();
			final String string = object == null ? WorkbenchMessages.Util_listNull : object.toString();
			if (list == null) {
				list = string;
			} else {
				list = createList(list, string);
			}
		}
		return safeString(list);
	}

	/**
	 * Creates a {@link String} representing the elements in <code>items</code> as a
	 * list. This method uses the {@link Object#toString()} method on the objects to
	 * create them as a String.
	 *
	 * @param items the array to make into a String
	 * @return a string which presents <code>items</code> in String form.
	 */
	public static String createList(Object[] items) {
		String list = null;
		for (Object item : items) {
			if (list == null) {
				list = item.toString();
			} else {
				list = createList(list, item.toString());
			}
		}
		return safeString(list);
	}

