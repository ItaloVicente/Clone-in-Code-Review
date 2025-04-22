 * The first step uses the values returned from <code>category</code>.
 * By default, all elements are in the same category.
 * The second level uses strings obtained from the content viewer's label
 * provider via <code>ILabelProvider.getText()</code>.
 * The strings are compared using a comparator from {@link Policy#getComparator()}
 * which by default does a case sensitive string comparison.
