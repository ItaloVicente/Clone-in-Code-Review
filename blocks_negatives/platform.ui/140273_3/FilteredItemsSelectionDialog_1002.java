	 * Depending on the filter, <code>FilterJob</code> decides which kind of
	 * search will be run inside <code>filterContent</code>. If the last
	 * filtering is done (last completed filter), is not null, and the new
	 * filter is a sub-filter ({@link FilteredItemsSelectionDialog.ItemsFilter#isSubFilter(FilteredItemsSelectionDialog.ItemsFilter)})
	 * of the last, then <code>FilterJob</code> only filters in the cache. If
	 * it is the first filtering or the new filter isn't a sub-filter of the
	 * last one, a full search is run.
