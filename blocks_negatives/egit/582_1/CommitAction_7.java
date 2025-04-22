	private void writeTreeWithSubTrees(Tree tree) throws TeamException {
		if (tree.getId() == null) {
			if (GitTraceLocation.UI.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.UI.getLocation(),
			try {
				for (TreeEntry entry : tree.members()) {
					if (entry.isModified()) {
						if (entry instanceof Tree) {
							writeTreeWithSubTrees((Tree) entry);
						} else {
							if (GitTraceLocation.UI.isActive())
								GitTraceLocation.getTrace().trace(
										GitTraceLocation.UI.getLocation(),
												+ entry.getFullName());
						}
