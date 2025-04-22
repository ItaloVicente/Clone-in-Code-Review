		return new Comparator<Object>() {
			/**
			 * Compares string s1 to string s2.
			 *
			 * @param s1
			 *            string 1
			 * @param s2
			 *            string 2
			 * @return Returns an integer value. Value is less than zero if
			 *         source is less than target, value is zero if source and
			 *         target are equal, value is greater than zero if source is
			 *         greater than target.
			 * @exception ClassCastException
			 *                the arguments cannot be cast to Strings.
			 */
			@Override
			public int compare(Object s1, Object s2) {
				return ((String) s1).compareTo((String) s2);
			}
		};
