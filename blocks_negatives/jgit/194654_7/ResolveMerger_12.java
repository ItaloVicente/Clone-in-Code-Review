	 * <ul>
	 * <li>the index entry does not match the entry in ours. When merging one
	 * branch into the current HEAD, ours will point to HEAD and theirs will
	 * point to the other branch. It is assumed that the index matches the HEAD
	 * because it will only not match HEAD if it was populated before the merge
	 * operation. But the merge commit should not accidentally contain
	 * modifications done before the merge. Check the <a href=
	 * >git read-tree</a> documentation for further explanations.</li>
	 * <li>A conflict was detected and the working-tree file is dirty. When a
	 * conflict is detected the content-merge algorithm will try to write a
	 * merged version into the working-tree. If the file is dirty we would
	 * override unsaved data.</li>
	 * </ul>
	 *
