	public void setTreeId(final ObjectId id) {
		if (treeId==null || !treeId.equals(id)) {
			treeObj = null;
		}
		treeId = id;
	}

	public Tree getTree() throws IOException {
		if (treeObj == null) {
			treeObj = objdb.mapTree(getTreeId());
			if (treeObj == null) {
				throw new MissingObjectException(getTreeId(),
						Constants.TYPE_TREE);
			}
		}
		return treeObj;
	}

	/**
	 * Set the tree object for this commit
	 * @see #setTreeId
	 * @param t the Tree object
	 */
	public void setTree(final Tree t) {
		treeId = t.getTreeId();
		treeObj = t;
