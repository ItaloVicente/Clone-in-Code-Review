 * working-tree. In contrast to {@link TreeFilter#ANY_DIFF} this filter takes
 * care to first compare the entry from the {@link DirCacheIterator} with the
 * entries from all other iterators besides the {@link WorkingTreeIterator}.
 * Since all those entries have fast access to content ids that is very fast. If
 * a difference is detected in this step this filter decides to include that
 * path before even looking at the working-tree entry.
